package org.iplacex.proyectos.discografia.artistas;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IartistaRepository extends MongoRepository<artista, ObjectId> {

}
