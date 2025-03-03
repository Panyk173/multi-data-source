package com.example.multidatasource.controller;

import com.example.multidatasource.dto.AlumnoDTO;
import com.example.multidatasource.service.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para Alumnos (TXT).
 * Expone endpoints para listar, obtener, crear, actualizar y eliminar alumnos.
 */
@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    // GET /alumnos - Lista todos los alumnos
    @GetMapping
    public List<AlumnoDTO> listarAlumnos() {
        return alumnoService.getAllAlumnos();
    }

    // GET /alumnos/{id} - Obtiene un alumno por su ID
    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDTO> obtenerAlumno(@PathVariable int id) {
        AlumnoDTO alumno = alumnoService.getAlumno(id);
        if (alumno == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(alumno);
    }

    // POST /alumnos - Crea un nuevo alumno
    @PostMapping
    public ResponseEntity<AlumnoDTO> crearAlumno(@RequestBody AlumnoDTO dto) {
        try {
            AlumnoDTO nuevo = alumnoService.createAlumno(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // PUT /alumnos/{id} - Actualiza un alumno existente
    @PutMapping("/{id}")
    public ResponseEntity<AlumnoDTO> actualizarAlumno(@PathVariable int id, @RequestBody AlumnoDTO dto) {
        try {
            AlumnoDTO actualizado = alumnoService.updateAlumno(id, dto);
            if (actualizado == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // DELETE /alumnos/{id} - Elimina un alumno por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable int id) {
        boolean eliminado = alumnoService.deleteAlumno(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
