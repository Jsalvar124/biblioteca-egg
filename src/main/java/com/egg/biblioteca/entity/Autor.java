package com.egg.biblioteca.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="autor_id")
    private UUID id;
    private String nombre;

    public Autor() {
    }

    public Autor(String nombre) {
        this.nombre = nombre;
    }

    public Autor(UUID id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
