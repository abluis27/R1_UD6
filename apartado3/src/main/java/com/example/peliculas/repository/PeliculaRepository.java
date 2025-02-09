package com.example.peliculas.repository;

import com.example.peliculas.model.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {
    // Y se busca por mas de un campo hay que separarlos por And
    Optional<Pelicula> findByNombreAndDirectorAndClasificacion(String nombre, String director, String clasificacion);
}
