package com.example.multidatasource.repository;

import com.example.multidatasource.model.Alumno;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// DAO para gestionar Alumnos en archivo de texto
@Repository
public class AlumnoDAO {
    @Value("${app.alumnos.file}")
    private String txtFilePath;

    public List<Alumno> findAll() {
        List<Alumno> alumnos = new ArrayList<>();
        Path path = Paths.get(txtFilePath);
        if (!Files.exists(path)) {
            return alumnos;
        }
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String line : lines) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    int id = Integer.parseInt(parts[0]);
                    String nombre = parts[1];
                    int edad = Integer.parseInt(parts[2]);
                    alumnos.add(new Alumno(id, nombre, edad));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de alumnos", e);
        }
        return alumnos;
    }
}
