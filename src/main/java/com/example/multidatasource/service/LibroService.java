package com.example.multidatasource.service;

import com.example.multidatasource.dto.LibroDTO;
import com.example.multidatasource.model.Libro;
import com.example.multidatasource.repository.LibroDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para la logica de negocio de Libros (XML).
 * Realiza validaciones basicas y mapea entre Libro y LibroDTO.
 */
@Service
public class LibroService {

    @Autowired
    private LibroDAO libroDAO;

    // Lista todos los libros
    public List<LibroDTO> getAllLibros() {
        List<Libro> libros = libroDAO.findAll();
        List<LibroDTO> dtos = new ArrayList<>();
        for (Libro l : libros) {
            dtos.add(new LibroDTO(l.getId(), l.getTitulo(), l.getAutor(), l.getPrecio()));
        }
        return dtos;
    }

    // Obtiene un libro por ID
    public LibroDTO getLibro(int id) {
        Libro libro = libroDAO.findById(id);
        if (libro == null) {
            return null;
        }
        return new LibroDTO(libro.getId(), libro.getTitulo(), libro.getAutor(), libro.getPrecio());
    }

    // Crea un nuevo libro
    public LibroDTO createLibro(LibroDTO dto) {
        // Validaciones basicas
        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El titulo del libro no puede estar vacio");
        }
        if (dto.getPrecio() != null && dto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        Libro libro = new Libro();
        libro.setId(0);  // Forzar generacion de nuevo ID en el DAO
        libro.setTitulo(dto.getTitulo());
        libro.setAutor(dto.getAutor());
        libro.setPrecio(dto.getPrecio() != null ? dto.getPrecio() : 0.0);
        Libro guardado = libroDAO.save(libro);
        return new LibroDTO(guardado.getId(), guardado.getTitulo(), guardado.getAutor(), guardado.getPrecio());
    }

    // Actualiza un libro existente
    public LibroDTO updateLibro(int id, LibroDTO dto) {
        if (dto.getTitulo() == null || dto.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("El titulo del libro no puede estar vacio");
        }
        if (dto.getPrecio() != null && dto.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        Libro libro = new Libro(id, dto.getTitulo(), dto.getAutor(), dto.getPrecio() != null ? dto.getPrecio() : 0.0);
        Libro actualizado = libroDAO.update(libro);
        if (actualizado == null) {
            return null;
        }
        return new LibroDTO(actualizado.getId(), actualizado.getTitulo(), actualizado.getAutor(), actualizado.getPrecio());
    }

    // Elimina un libro
    public boolean deleteLibro(int id) {
        return libroDAO.delete(id);
    }
}
