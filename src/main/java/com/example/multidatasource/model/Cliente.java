package com.example.multidatasource.model;


import jakarta.persistence.*;

// Entidad JPA para Clientes en MariaDB
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private Double saldo;

    public Cliente() {}

    public Cliente(Long id, String nombre, String email, Double saldo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.saldo = saldo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Double getSaldo() { return saldo; }
    public void setSaldo(Double saldo) { this.saldo = saldo; }
}
