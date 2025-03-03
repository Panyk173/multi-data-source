package com.example.multidatasource.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

// Clase auxiliar JAXB para contener una lista de libros en XML
@XmlRootElement(name = "libros")
@XmlAccessorType(XmlAccessType.FIELD)
public class LibrosWrapper {
    @XmlElement(name = "libro")
    private List<Libro> libros = new ArrayList<>();

    public List<Libro> getLibros() { return libros; }
    public void setLibros(List<Libro> libros) { this.libros = libros; }
}
