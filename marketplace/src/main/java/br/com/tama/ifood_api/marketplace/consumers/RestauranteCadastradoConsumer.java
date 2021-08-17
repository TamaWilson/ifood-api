package br.com.tama.ifood_api.marketplace.consumers;

import br.com.tama.ifood_api.marketplace.models.entities.Restaurante;
import io.smallrye.reactive.messaging.annotations.Merge;
import io.vertx.mutiny.pgclient.PgPool;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RestauranteCadastradoConsumer {

    @Inject
    PgPool pgPool;

    @Merge
    @Incoming("restaurantes")
    public void receberRestaurantes(Restaurante  restaurante){
        restaurante.persist(pgPool);
    }
}
