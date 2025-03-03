package com.example.multidatasource.repository;

import com.example.multidatasource.model.Libro;
import com.example.multidatasource.model.LibrosWrapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

// DAO para gestionar Libros en archivo XML usando JAXB
@Repository
public class LibroDAO {
    @Value("${app.libros.file}")
    private String xmlFilePath;

    private JAXBContext jaxbContext;

    public LibroDAO() {
        try {
            jaxbContext = JAXBContext.newInstance(LibrosWrapper.class);
        } catch (JAXBException e) {
            throw new RuntimeException("Error al inicializar JAXB", e);
        }
    }

    public List<Libro> findAll() {
        try {
            File file = new File(xmlFilePath);
            if (!file.exists()) {
                return List.of();
            }
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            LibrosWrapper wrapper = (LibrosWrapper) unmarshaller.unmarshal(file);
            return wrapper.getLibros();
        } catch (JAXBException e) {
            throw new RuntimeException("Error al leer el archivo XML de libros", e);
        }
    }
}
