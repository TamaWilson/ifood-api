package br.com.tama.ifood_api.marketplace.models.dto;

import br.com.tama.ifood_api.marketplace.models.entities.Restaurante;
import io.vertx.mutiny.sqlclient.Row;

import java.math.BigDecimal;

public class PratoDTO {

    public Long id;

    public String nome;

    public String descricao;

    public BigDecimal preco;

    public static PratoDTO from(Row row) {
        PratoDTO dto = new PratoDTO();
        dto.id = row.getLong("id");
        dto.descricao = row.getString("descricao");
        dto.nome = row.getString("nome");
        dto.preco = row.getBigDecimal("preco");
        return dto;
    }
}
