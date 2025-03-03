package com.example.multidatasource.model;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

// Entidad JAXB para Libro en archivo XML
@XmlRootElement(name = "libro")
@XmlAccessorType(XmlAccessType.FIELD)
public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private double precio;

    public Libro() {}

    public Libro(int id, String titulo, String autor, double precio) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}
