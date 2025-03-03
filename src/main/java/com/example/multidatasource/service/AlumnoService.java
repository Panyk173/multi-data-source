package com.example.multidatasource.service;

import com.example.multidatasource.dto.AlumnoDTO;
import com.example.multidatasource.model.Alumno;
import com.example.multidatasource.repository.AlumnoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio para la logica de negocio de Alumnos (Texto).
 * Realiza validaciones basicas y mapea entre Alumno y AlumnoDTO.
 */
@Service
public class AlumnoService {

    @Autowired
    private AlumnoDAO alumnoDAO;

    // Lista todos los alumnos
    public List<AlumnoDTO> getAllAlumnos() {
        List<Alumno> alumnos = alumnoDAO.findAll();
        List<AlumnoDTO> dtos = new ArrayList<>();
        for (Alumno a : alumnos) {
            dtos.add(new AlumnoDTO(a.getId(), a.getNombre(), a.getEdad()));
        }
        return dtos;
    }

    // Obtiene un alumno por ID
    public AlumnoDTO getAlumno(int id) {
        Alumno alumno = alumnoDAO.findById(id);
        if (alumno == null) {
            return null;
        }
        return new AlumnoDTO(alumno.getId(), alumno.getNombre(), alumno.getEdad());
    }

    // Crea un nuevo alumno
    public AlumnoDTO createAlumno(AlumnoDTO dto) {
        // Validaciones basicas
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del alumno no puede estar vacio");
        }
        if (dto.getEdad() != null && dto.getEdad() < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
        Alumno alumno = new Alumno();
        alumno.setId(0);  // Forzar generacion de nuevo ID
        alumno.setNombre(dto.getNombre());
        alumno.setEdad(dto.getEdad() != null ? dto.getEdad() : 0);
        Alumno guardado = alumnoDAO.save(alumno);
        return new AlumnoDTO(guardado.getId(), guardado.getNombre(), guardado.getEdad());
    }

    // Actualiza un alumno existente
    public AlumnoDTO updateAlumno(int id, AlumnoDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del alumno no puede estar vacio");
        }
        if (dto.getEdad() != null && dto.getEdad() < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
        Alumno alumno = new Alumno(id, dto.getNombre(), dto.getEdad() != null ? dto.getEdad() : 0);
        Alumno actualizado = alumnoDAO.update(alumno);
        if (actualizado == null) {
            return null;
        }
        return new AlumnoDTO(actualizado.getId(), actualizado.getNombre(), actualizado.getEdad());
    }

    // Elimina un alumno
    public boolean deleteAlumno(int id) {
        return alumnoDAO.delete(id);
    }
}
