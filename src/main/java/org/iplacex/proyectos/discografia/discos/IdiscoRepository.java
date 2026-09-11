package org.iplacex.proyectos.discografia.discos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface IdiscoRepository extends MongoRepository<disco, String> {

    @Query("{ 'idArtista': ?0 }")
    List<disco> findDiscosByIdArtista(String idArtista);
}
