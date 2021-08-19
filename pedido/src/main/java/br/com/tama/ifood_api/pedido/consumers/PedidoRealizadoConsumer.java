package br.com.tama.ifood_api.pedido.consumers;

import br.com.tama.ifood_api.pedido.config.ESService;
import br.com.tama.ifood_api.pedido.models.dto.PedidoRealizadoDTO;
import br.com.tama.ifood_api.pedido.models.dto.PratoPedidoDTO;
import br.com.tama.ifood_api.pedido.models.entities.Pedido;
import br.com.tama.ifood_api.pedido.models.entities.Prato;
import br.com.tama.ifood_api.pedido.models.entities.Restaurante;
import org.bson.types.Decimal128;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import java.util.ArrayList;

public class PedidoRealizadoConsumer {

    @Inject
    ESService elastic;

    @Incoming("pedidos")
    public void lerPedidos(PedidoRealizadoDTO dto){
        Pedido pedido = new Pedido();

        pedido.cliente = dto.cliente;
        pedido.pratos = new ArrayList<>();
        dto.pratos.forEach(prato -> pedido.pratos.add(from(prato)));

        Restaurante restaurante = new Restaurante();
        restaurante.nome = dto.restaurante.nome;

        pedido.restaurante = restaurante;

        String json = JsonbBuilder.create().toJson(dto);

        elastic.index("pedidos", json);
        pedido.persist();
    }

    private Prato from(PratoPedidoDTO dto) {
        Prato prato = new Prato();
        prato.descricao = dto.descricao;
        prato.nome = dto.nome;
        prato.preco = new Decimal128(dto.preco);

        return prato;
    }
}
