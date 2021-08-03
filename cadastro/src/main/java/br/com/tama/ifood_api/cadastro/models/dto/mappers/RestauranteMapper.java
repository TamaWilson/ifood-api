package br.com.tama.ifood_api.cadastro.models.dto.mappers;

import br.com.tama.ifood_api.cadastro.models.dto.AdicionarRestauranteDTO;
import br.com.tama.ifood_api.cadastro.models.dto.AtualizarRestauranteDTO;
import br.com.tama.ifood_api.cadastro.models.dto.RestauranteDTO;
import br.com.tama.ifood_api.cadastro.models.entities.Restaurante;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

    @Mapping(target = "nome", source = "nomeFantasia" )
    @Mapping(target = "id", ignore = true)
    @Mapping(target= "dataCriacao", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "localizacao.id", ignore = true)
    Restaurante toRestaurante(AdicionarRestauranteDTO dto);

    @Mapping(target = "nome", source = "nomeFantasia")
    void toRestaurante(AtualizarRestauranteDTO dto, @MappingTarget Restaurante restaurante);

    @Mapping(target = "nomeFantasia", source = "nome")
    @Mapping(target = "dataCriacao", dateFormat = "dd/MM/yyyy HH:mm:ss")
    RestauranteDTO toRestauranteDTO(Restaurante restaurante);
}
