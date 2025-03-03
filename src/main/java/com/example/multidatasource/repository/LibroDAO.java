package com.example.multidatasource.repository;

import com.example.multidatasource.model.Libro;
import com.example.multidatasource.model.LibrosWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DAO para gestionar Libros en archivo XML usando JAXB.
 * Se implementan los metodos findAll, findById, save, update y delete.
 */
@Repository
public class LibroDAO {

    @Value("${app.libros.file}")
    private String xmlFilePath;  // Ruta del archivo XML, definida en application.properties

    private JAXBContext jaxbContext;

    public LibroDAO() {
        try {
            // Inicializa el contexto JAXB para LibrosWrapper
            jaxbContext = JAXBContext.newInstance(LibrosWrapper.class);
        } catch (JAXBException e) {
            throw new RuntimeException("Error al inicializar JAXB", e);
        }
    }

    // Retorna la lista de todos los libros del archivo XML
    public List<Libro> findAll() {
        try {
            File file = new File(xmlFilePath);
            if (!file.exists()) {
                return List.of();  // Retorna lista vacia si el archivo no existe
            }
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            LibrosWrapper wrapper = (LibrosWrapper) unmarshaller.unmarshal(file);
            return wrapper.getLibros();
        } catch (JAXBException e) {
            throw new RuntimeException("Error al leer el archivo XML de libros", e);
        }
    }

    // Retorna un libro por su ID
    public Libro findById(int id) {
        List<Libro> libros = findAll();
        return libros.stream()
                .filter(l -> l.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Guarda un nuevo libro en el archivo XML y lo retorna
    public Libro save(Libro libro) {
        List<Libro> libros = findAll();
        // Si el ID es menor o igual a 0, se asigna un nuevo ID (maximo + 1)
        if (libro.getId() <= 0) {
            int newId = libros.stream().mapToInt(Libro::getId).max().orElse(0) + 1;
            libro.setId(newId);
        }
        libros.add(libro);
        writeLibros(libros);
        return libro;
    }

    // Actualiza un libro existente; retorna el libro actualizado o null si no se encontro
    public Libro update(Libro libro) {
        List<Libro> libros = findAll();
        boolean updated = false;
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).getId() == libro.getId()) {
                libros.set(i, libro);
                updated = true;
                break;
            }
        }
        if (!updated) {
            return null;
        }
        writeLibros(libros);
        return libro;
    }

    // Elimina un libro por su ID; retorna true si se elimino, false en caso contrario
    public boolean delete(int id) {
        List<Libro> libros = findAll();
        List<Libro> filtrados = libros.stream()
                .filter(l -> l.getId() != id)
                .collect(Collectors.toList());
        if (filtrados.size() == libros.size()) {
            return false;  // No se encontro el libro a eliminar
        }
        writeLibros(filtrados);
        return true;
    }

    // Metodo privado para escribir la lista de libros al archivo XML
    private void writeLibros(List<Libro> libros) {
        try {
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            LibrosWrapper wrapper = new LibrosWrapper();
            wrapper.setLibros(libros);
            marshaller.marshal(wrapper, new File(xmlFilePath));
        } catch (JAXBException e) {
            throw new RuntimeException("Error al escribir en el archivo XML de libros", e);
        }
    }
}
