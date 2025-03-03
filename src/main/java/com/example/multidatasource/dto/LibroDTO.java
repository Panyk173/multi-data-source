package com.example.multidatasource.dto;

// DTO para Libro (XML)
public class LibroDTO {
    private Integer id;
    private String titulo;
    private String autor;
    private Double precio;

    public LibroDTO() {}

    public LibroDTO(Integer id, String titulo, String autor, Double precio) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.precio = precio;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
}
