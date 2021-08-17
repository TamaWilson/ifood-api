package br.com.tama.ifood_api.marketplace.models.entities;

import br.com.tama.ifood_api.marketplace.models.dto.PratoDTO;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;

import java.math.BigDecimal;

import static br.com.tama.ifood_api.marketplace.utils.MutinyUtils.uniToMulti;

public class Prato {

    public Long id;

    public String nome;

    public String descricao;

    public Restaurante restaurante;

    public BigDecimal preco;

    public static Multi<PratoDTO> findAll(PgPool pgPool) {
        Uni<RowSet<Row>> preparedQuery = pgPool.preparedQuery("select * from prato").execute();
        return uniToMulti(preparedQuery);
    }

    public static Multi<PratoDTO> findAll(PgPool client, Long idRestaurante) {
        Uni<RowSet<Row>> preparedQuery = client
                .preparedQuery("SELECT * FROM prato where prato.restaurante_id = $1 ORDER BY nome ASC").execute(
                        Tuple.of(idRestaurante));
        return uniToMulti(preparedQuery);

    }

    public static Uni<PratoDTO> findById(PgPool client, Long id) {
        return client.preparedQuery("SELECT * FROM prato WHERE id = $1").execute(Tuple.of(id))
                .map(RowSet::iterator)
                .map(iterator -> iterator.hasNext() ? PratoDTO.from(iterator.next()) : null);
    }

}
