package br.com.tama.ifood_api.pedido.models.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

import java.util.List;

@MongoEntity(collection = "pedido", database = "pedido")
public class Pedido  extends PanacheMongoEntity {

    public String cliente;

    public Restaurante restaurante;

    public List<Prato> pratos;

    public String entregador;

    public Localizacao localizacao;
}
