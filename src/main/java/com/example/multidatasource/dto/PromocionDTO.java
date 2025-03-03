package com.example.multidatasource.dto;

// DTO para Promocion (MongoDB)
public class PromocionDTO {
    private String id;
    private String nombre;
    private Double descuento;

    public PromocionDTO() {}

    public PromocionDTO(String id, String nombre, Double descuento) {
        this.id = id;
        this.nombre = nombre;
        this.descuento = descuento;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getDescuento() { return descuento; }
    public void setDescuento(Double descuento) { this.descuento = descuento; }
}
