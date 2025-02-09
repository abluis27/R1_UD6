package com.example.peliculas.controller;

import com.example.peliculas.model.Pelicula;
import com.example.peliculas.repository.PeliculaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/peliculas")
public class PeliculaController {
    private PeliculaRepository repositorioPeliculas;

    public PeliculaController(PeliculaRepository repositorioPeliculas) {
        this.repositorioPeliculas = repositorioPeliculas;
    }

    // GET - Listar todas las peliculas
    @GetMapping
    public ResponseEntity<List<Pelicula>> getAllPeliculas() {
        List<Pelicula> peliculas = repositorioPeliculas.findAll();
        return ResponseEntity.ok(peliculas);
    }

    // GET - Obtener pelicula por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPeliculaById(@PathVariable Long id) {
        ResponseEntity<?> conflicto = verificarConflictos(id);
        if(conflicto != null) {
           return conflicto;
        }

        Optional<Pelicula> peliculaBuscada = repositorioPeliculas.findById(id);
        return ResponseEntity.ok().body(peliculaBuscada.get());
    }

    private ResponseEntity<?> verificarConflictos(Long id) {
        if(id == null) {
            return ResponseEntity.status(400).body("Se debe de especificar la ID");
        }
        Optional<Pelicula> pelicula = repositorioPeliculas.findById(id);
        if(pelicula.isEmpty()) {
            return ResponseEntity.status(404).body("No existe la pelicula con la ID " + id);
        }
        return null;
    }

    // POST
    @PostMapping
    public ResponseEntity<?> crearPelicula(@RequestBody Pelicula nuevaPelicula) {
        // Comprobamos que la id no este registrada
        if(nuevaPelicula.getId() != 0) {
            Optional<Pelicula> pelicula = repositorioPeliculas.findById(nuevaPelicula.getId());
            if(pelicula.isPresent()) {
                return ResponseEntity.status(409).body("Ya hay una pelicula regitrada con la ID "
                        + nuevaPelicula.getId());
            }
        }

        // Comprobamos que los datos no esten registrados
        Optional<Pelicula> peliculaParaRegistrar = repositorioPeliculas.findByNombreAndDirectorAndClasificacion(
                nuevaPelicula.getNombre(), nuevaPelicula.getDirector(), nuevaPelicula.getClasificacion()
        );
        if(peliculaParaRegistrar.isPresent()) {
            return ResponseEntity.status(409).body("Los datos de la pelicula ya estan registrados");
        }

        // Hasta este punto no ha habido ningun conflicto, por lo que se guarda la pelicula
        Pelicula peliculaIntroducida = repositorioPeliculas.save(nuevaPelicula);
        return ResponseEntity.status(201).body(peliculaIntroducida);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarPeliculaPorId(@PathVariable Long id) {
        ResponseEntity<?> conflicto = verificarConflictos(id);
        if(conflicto != null) {
            return conflicto;
        }

        Optional<Pelicula> peliculaParaEliminar = repositorioPeliculas.findById(id);
        repositorioPeliculas.deleteById(id);
        // Podemos utilizar ResponseEntity.noContent().build(); para devolver el codigo 204 sin cuerpo
        // Pero en este caso se devolvera la entidad eliminada
        return ResponseEntity.ok().body(peliculaParaEliminar.get());
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPelicula(@PathVariable Long id, @RequestBody Pelicula nuevosDatosPelicula) {
        ResponseEntity<?> conflicto = verificarConflictos(id);
        if(conflicto != null) {
            return conflicto;
        }

        Optional<Pelicula> peliculaOptional = repositorioPeliculas.findById(id);
        Pelicula peliculaParaModificar = peliculaOptional.get();
        modificarPelicula(peliculaParaModificar, nuevosDatosPelicula);
        Pelicula peliculaModificada = repositorioPeliculas.save(peliculaParaModificar);
        return ResponseEntity.ok(peliculaModificada);
    }

    private void modificarPelicula(Pelicula peliculaParaModificar, Pelicula nuevosDatosPelicula) {
        peliculaParaModificar.setNombre(nuevosDatosPelicula.getNombre());
        peliculaParaModificar.setDirector(nuevosDatosPelicula.getDirector());
        peliculaParaModificar.setClasificacion(nuevosDatosPelicula.getClasificacion());
    }

    // PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcialPelicula(@PathVariable Long id, @RequestBody Pelicula nuevosDatosPelicula) {
        ResponseEntity<?> conflicto = verificarConflictos(id);
        if(conflicto != null) {
            return conflicto;
        }

        Optional<Pelicula> pelicula = repositorioPeliculas.findById(id);
        Pelicula peliculaParaModificar = getPelicula(nuevosDatosPelicula, pelicula);
        Pelicula peliculaActualizada = repositorioPeliculas.save(peliculaParaModificar);
        return ResponseEntity.ok(peliculaActualizada);
    }

    private Pelicula getPelicula(Pelicula nuevosDatosPelicula, Optional<Pelicula> pelicula) {
        Pelicula peliculaParaModificar = pelicula.get();

        if (nuevosDatosPelicula.getNombre() != null) {
            peliculaParaModificar.setNombre(nuevosDatosPelicula.getNombre());
        }
        if (nuevosDatosPelicula.getDirector() != null) {
            peliculaParaModificar.setDirector(nuevosDatosPelicula.getDirector());
        }
        if (nuevosDatosPelicula.getClasificacion() != null) {
            peliculaParaModificar.setClasificacion(nuevosDatosPelicula.getClasificacion());
        }
        return peliculaParaModificar;
    }
}
