package br.com.tama.ifood_api.marketplace.utils;

import br.com.tama.ifood_api.marketplace.models.dto.PratoDTO;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

import java.util.stream.StreamSupport;

public class MutinyUtils {
    public static Multi<PratoDTO> uniToMulti(Uni<RowSet<Row>> queryResult) {
        return queryResult.onItem().transformToMulti(set -> Multi.createFrom().items(
                () -> StreamSupport.stream(set.spliterator(), false))).onItem().transform(PratoDTO::from);
    }
}
