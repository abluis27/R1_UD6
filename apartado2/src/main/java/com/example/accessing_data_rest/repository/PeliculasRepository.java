package com.example.accessing_data_rest.repository;

import com.example.accessing_data_rest.model.Pelicula;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "peliculas", path = "peliculas")
public interface PeliculasRepository
        extends PagingAndSortingRepository<Pelicula, Long>
        , CrudRepository<Pelicula, Long>
{

}
