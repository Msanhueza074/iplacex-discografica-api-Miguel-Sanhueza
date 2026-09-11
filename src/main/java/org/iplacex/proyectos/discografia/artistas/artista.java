package org.iplacex.proyectos.discografia.artistas;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

@Document("artistas")
public class artista {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    public ObjectId Id;

    public String nombre;

    public List<String> estilos;

    public int anioFundacion;

    public boolean estaActivo;
}