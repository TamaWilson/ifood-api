package br.com.tama.ifood_api.pedido.models.dto;

import java.util.List;

public class PedidoRealizadoDTO {

    public List<PratoPedidoDTO> pratos;

    public RestauranteDTO restaurante;

    public String cliente;
}
