package org.iplacex.proyectos.discografia.discos;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/api")
@CrossOrigin
public class discoController {

    @Autowired
    private IdiscoRepository discoRepo;


    @PostMapping(
    value = "/disco",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<disco> HandlePostDiscoRequest(
        @RequestBody disco disco) {

    System.out.println("ID ARTISTA RECIBIDO: " + disco.idArtista);

    disco temp = discoRepo.insert(disco);

    return new ResponseEntity<>(temp, HttpStatus.CREATED);
}

    @GetMapping(
        value = "/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<disco>> HandleGetDiscosRequest() {

        List<disco> discos = discoRepo.findAll();

        return new ResponseEntity<>(discos, HttpStatus.OK);
    }


    @GetMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<disco> HandleGetDiscoRequest(
            @PathVariable("id") String id) {

        Optional<disco> temp = discoRepo.findById(id);

        if (!temp.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return new ResponseEntity<>(temp.get(), HttpStatus.OK);
    }

    @GetMapping(
        value = "/artista/{id}/discos",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<disco>> HandleGetDiscosByArtistaRequest(
            @PathVariable("id") String id) {

        List<disco> discos = discoRepo.findDiscosByIdArtista(id);

        return new ResponseEntity<>(discos, HttpStatus.OK);
    }

    @PutMapping(
    value = "/disco/{id}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<disco> HandleUpdateDiscoRequest(
        @PathVariable("id") String id,
        @RequestBody disco disco) {

    Optional<disco> existente = discoRepo.findById(id);

    if (!existente.isPresent()) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }

    disco.id = id;

    disco temp = discoRepo.save(disco);

    return new ResponseEntity<>(temp, HttpStatus.OK);
}

    @DeleteMapping(
        value = "/disco/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<disco> HandleDeleteDiscoRequest(
            @PathVariable("id") String id) {

        Optional<disco> existente = discoRepo.findById(id);

        if (!existente.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        disco temp = existente.get();

        discoRepo.deleteById(id);

        return new ResponseEntity<>(temp, HttpStatus.OK);
    }
}