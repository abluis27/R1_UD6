package com.example.peliculas.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "pelicula")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "director")
    private String director;
    @Column(name = "clasificacion")
    private String clasificacion;

    public Pelicula(String nombre, String director, String clasificacion) {
        this.nombre = nombre;
        this.director = director;
        this.clasificacion = clasificacion;
    }

    public Pelicula() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pelicula pelicula)) return false;
        return id == pelicula.id
                && Objects.equals(nombre, pelicula.nombre)
                && Objects.equals(director, pelicula.director)
                && Objects.equals(clasificacion, pelicula.clasificacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, director, clasificacion);
    }
}
