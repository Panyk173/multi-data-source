package com.example.multidatasource.controller;

import com.example.multidatasource.dto.LibroDTO;
import com.example.multidatasource.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para Libros (XML).
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar libros.
 */
@RestController
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    // GET /libros - Lista todos los libros
    @GetMapping
    public List<LibroDTO> listarLibros() {
        return libroService.getAllLibros();
    }

    // GET /libros/{id} - Obtiene un libro por su ID
    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> obtenerLibro(@PathVariable int id) {
        LibroDTO libro = libroService.getLibro(id);
        if (libro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(libro);
    }

    // POST /libros - Crea un nuevo libro
    @PostMapping
    public ResponseEntity<LibroDTO> crearLibro(@RequestBody LibroDTO dto) {
        try {
            LibroDTO nuevo = libroService.createLibro(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /libros/{id} - Actualiza un libro existente
    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> actualizarLibro(@PathVariable int id, @RequestBody LibroDTO dto) {
        try {
            LibroDTO actualizado = libroService.updateLibro(id, dto);
            if (actualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /libros/{id} - Elimina un libro por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable int id) {
        boolean eliminado = libroService.deleteLibro(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
