package org.iplacex.proyectos.discografia.artistas;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class artistaController {

    @Autowired
    private IartistaRepository artistaRepo;

    @Autowired
    private MongoTemplate mongoTemplate;


    @PostMapping(
        value = "/artista",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<artista> HandleInsertartistaRequest(
            @RequestBody artista artista) {

        artista temp = artistaRepo.insert(artista);

        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }


    @GetMapping(
        value = "/artistas",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<artista>> HandleGetartistasRequest() {

        List<artista> artistas = artistaRepo.findAll();

        return new ResponseEntity<>(artistas, HttpStatus.OK);
    }

    @GetMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> HandleGetartistaRequest(
            @PathVariable("id") String id) {

        if (!ObjectId.isValid(id)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("ID inválido");
        }

        ObjectId objectId = new ObjectId(id);

        MongoCollection<Document> collection =
                mongoTemplate.getCollection("artistas");

        Document documento =
                collection.find(Filters.eq("_id", objectId)).first();

        if (documento == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el artista");
        }

        artista resultado =
                mongoTemplate.getConverter().read(artista.class, documento);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping(
        value = "/artista/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> HandleUpdateartistaRequest(
            @PathVariable("id") String id,
            @RequestBody artista artista) {

        if (!ObjectId.isValid(id)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("ID inválido");
        }

        ObjectId objectId = new ObjectId(id);

        MongoCollection<Document> collection =
                mongoTemplate.getCollection("artistas");

        Document existente =
                collection.find(Filters.eq("_id", objectId)).first();

        if (existente == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el artista");
        }

        artista.Id = objectId;

        artista temp = mongoTemplate.save(artista, "artistas");

        return ResponseEntity.ok(temp);
    }

    @DeleteMapping(
        value = "/artista/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> HandleDeleteartistaRequest(
            @PathVariable("id") String id) {

        if (!ObjectId.isValid(id)) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("ID inválido");
        }

        ObjectId objectId = new ObjectId(id);

        MongoCollection<Document> collection =
                mongoTemplate.getCollection("artistas");

        Document existente =
                collection.find(Filters.eq("_id", objectId)).first();

        if (existente == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el artista");
        }

        collection.deleteOne(Filters.eq("_id", objectId));

        artista resultado =
                mongoTemplate.getConverter().read(artista.class, existente);

        return ResponseEntity.ok(resultado);
    }
}