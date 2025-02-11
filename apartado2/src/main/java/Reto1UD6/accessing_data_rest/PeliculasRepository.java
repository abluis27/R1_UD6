package Reto1UD6.accessing_data_rest;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "peliculas", path = "peliculas")
public interface PeliculasRepository extends PagingAndSortingRepository<Pelicula, Long>, CrudRepository<Pelicula,Long> {

  List<Pelicula> findByNombre(@Param("nombre") String nombre);

}