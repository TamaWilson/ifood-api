package br.com.tama.ifood_api.pedido.utils;

import br.com.tama.ifood_api.pedido.models.dto.PedidoRealizadoDTO;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;


public class PedidoDeserializer extends ObjectMapperDeserializer<PedidoRealizadoDTO> {

    public PedidoDeserializer() {
        super(PedidoRealizadoDTO.class);
    }

}
