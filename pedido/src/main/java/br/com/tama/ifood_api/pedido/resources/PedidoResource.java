package br.com.tama.ifood_api.pedido.resources;

import br.com.tama.ifood_api.pedido.models.entities.Localizacao;
import br.com.tama.ifood_api.pedido.models.entities.Pedido;
import io.vertx.core.Vertx;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.mutiny.core.eventbus.EventBus;
import org.bson.types.ObjectId;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    Vertx vertx;

    @Inject
    EventBus eventBus;

    void startup(@Observes Router router){
        router.route("/localizacoes").handler(localizacaoHandler());
    }

    private SockJSHandler localizacaoHandler(){
        SockJSHandler handler = SockJSHandler.create(vertx);
        PermittedOptions permittedOptions = new PermittedOptions();
        permittedOptions.setAddress("novaLocalizacao");
        SockJSBridgeOptions bridgeOptions = new SockJSBridgeOptions().addOutboundPermitted(permittedOptions);
        handler.bridge(bridgeOptions);

        return handler;
    }

    @GET
    public List<Pedido> getPedidos(){
        return Pedido.listAll();
    }

    @POST
    @Path("{idPedido}/localizacao")
    public Pedido novaLocalizacao(@PathParam("idPedido") String idPedido, Localizacao localizacao){

        Pedido pedido = Pedido.findById(new ObjectId(idPedido));
        pedido.localizacao = localizacao;
        pedido.persistOrUpdate();

        String json = JsonbBuilder.create().toJson(localizacao);
        eventBus.send("novaLocalizacao", json);

        return pedido;
    }


}
