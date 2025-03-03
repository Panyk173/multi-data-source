package com.example.multidatasource.repository;

import com.example.multidatasource.model.Alumno;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar Alumnos en archivo de texto.
 * Cada linea del archivo tiene el formato: id;nombre;edad
 * Se implementan los metodos findAll, findById, save, update y delete.
 */
@Repository
public class AlumnoDAO {

    @Value("${app.alumnos.file}")
    private String txtFilePath;  // Ruta del archivo TXT, definida en application.properties

    // Retorna la lista de todos los alumnos del archivo de texto
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

    // Retorna un alumno por su ID
    public Alumno findById(int id) {
        for (Alumno a : findAll()) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    // Guarda un nuevo alumno en el archivo y retorna el alumno guardado
    public Alumno save(Alumno alumno) {
        List<Alumno> alumnos = findAll();
        // Si el ID es menor o igual a 0, asignar nuevo ID (maximo + 1)
        if (alumno.getId() <= 0) {
            int newId = alumnos.stream().mapToInt(Alumno::getId).max().orElse(0) + 1;
            alumno.setId(newId);
        }
        alumnos.add(alumno);
        writeAlumnos(alumnos);
        return alumno;
    }

    // Actualiza un alumno existente; retorna el alumno actualizado o null si no se encontro
    public Alumno update(Alumno alumno) {
        List<Alumno> alumnos = findAll();
        boolean updated = false;
        for (int i = 0; i < alumnos.size(); i++) {
            if (alumnos.get(i).getId() == alumno.getId()) {
                alumnos.set(i, alumno);
                updated = true;
                break;
            }
        }
        if (!updated) {
            return null;
        }
        writeAlumnos(alumnos);
        return alumno;
    }

    // Elimina un alumno por su ID; retorna true si se elimino, false en caso contrario
    public boolean delete(int id) {
        List<Alumno> alumnos = findAll();
        int sizeInicial = alumnos.size();
        alumnos.removeIf(a -> a.getId() == id);
        if (alumnos.size() == sizeInicial) {
            return false;
        }
        writeAlumnos(alumnos);
        return true;
    }

    // Metodo privado para escribir la lista de alumnos en el archivo de texto
    private void writeAlumnos(List<Alumno> alumnos) {
        List<String> lines = new ArrayList<>();
        for (Alumno a : alumnos) {
            lines.add(a.getId() + ";" + a.getNombre() + ";" + a.getEdad());
        }
        try {
            Files.write(Paths.get(txtFilePath), lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en el archivo de alumnos", e);
        }
    }
}
