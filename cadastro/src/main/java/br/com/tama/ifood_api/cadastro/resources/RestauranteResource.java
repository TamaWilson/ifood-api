package br.com.tama.ifood_api.cadastro.resources;

import br.com.tama.ifood_api.cadastro.infra.exceptions.dto.ConstraintValidationReponse;
import br.com.tama.ifood_api.cadastro.models.dto.AdicionarRestauranteDTO;
import br.com.tama.ifood_api.cadastro.models.dto.AtualizarRestauranteDTO;
import br.com.tama.ifood_api.cadastro.models.dto.RestauranteDTO;
import br.com.tama.ifood_api.cadastro.models.dto.mappers.RestauranteMapper;
import br.com.tama.ifood_api.cadastro.models.entities.Prato;
import br.com.tama.ifood_api.cadastro.models.entities.Restaurante;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name="restaurante")
public class RestauranteResource {

    @Inject
    RestauranteMapper restauranteMapper;

    @GET
    public List<RestauranteDTO> hello(){
        Stream<Restaurante> restaurantes = Restaurante.streamAll();
        return restaurantes.map(r-> restauranteMapper.toRestauranteDTO(r)).collect(Collectors.toList());

    }

    @POST
    @Transactional
    @APIResponse(responseCode = "201", description = "Resutaurante cadastrado com sucesso")
    @APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ConstraintValidationReponse.class)))
    public Response adicionar(@Valid AdicionarRestauranteDTO dto){
        Restaurante restaurante = restauranteMapper.toRestaurante(dto);
        restaurante.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void atualizar(@PathParam("id") Long id, AtualizarRestauranteDTO dto){
        Optional<Restaurante> restauranteOP = Restaurante.findByIdOptional(id);
        if(restauranteOP.isEmpty()){
            throw new NotFoundException();
        }
        Restaurante restaurante = restauranteOP.get();
        restauranteMapper.toRestaurante(dto, restaurante);
        restaurante.persist();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deletar(@PathParam("id") Long id){
        Optional<Restaurante> restauranteOP = Restaurante.findByIdOptional(id);
        restauranteOP.ifPresentOrElse(Restaurante::delete,()-> {throw new NotFoundException();});
    }

    //Pratos

    @GET
    @Path("{idRestaurante}/pratos")
    @Tag(name="prato")
    public List<Prato> buscarPratos(@PathParam("idRestaurante") Long idRestaurante){
        Optional<Restaurante> restauranteOP = Restaurante.findByIdOptional(idRestaurante);
        if(restauranteOP.isEmpty()){
            throw new NotFoundException("Restaurante não existe");
        }
        return Prato.list("restaurante", restauranteOP.get());
    }

    @POST
    @Path("{idRestaurante}/pratos")
    @Transactional
    @Tag(name="prato")
    public Response adicionarPrato(@PathParam("idRestaurante") Long idRestaurante, Prato dto){
        Optional<Restaurante> restauranteOP = Restaurante.findByIdOptional(idRestaurante);
        if(restauranteOP.isEmpty()){
            throw new NotFoundException("Restaurante não existe");
        }
        Prato prato = new Prato();
        prato.nome = dto.nome;
        prato.descricao = dto.descricao;
        prato.preco = dto.preco;
        prato.restaurante = restauranteOP.get();
        prato.persist();
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{idRestaurante}/pratos/{id}")
    @Transactional
    @Tag(name="prato")
    public void atualizarPrato(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id,  Prato dto){
        Optional<Restaurante> restauranteOP = Restaurante.findByIdOptional(idRestaurante);
        if(restauranteOP.isEmpty()){
            throw new NotFoundException("Restaurante não existe");
        }

        Optional<Prato> pratoOP = Prato.findByIdOptional(id);

        if(pratoOP.isEmpty()){
            throw new NotFoundException("Prato não existe");
        }

        Prato prato = pratoOP.get();

        prato.preco = dto.preco;
        prato.persist();

    }

    @DELETE
    @Path("{idRestaurante}/pratos/{id}")
    @Transactional
    @Tag(name="prato")
    public void deletarPrato(@PathParam("idRestaurante") Long idRestaurante, @PathParam("id") Long id){
        Optional<Restaurante> restauranteOP = Restaurante.findByIdOptional(idRestaurante);
        if(restauranteOP.isEmpty()){
            throw new NotFoundException("Restaurante não existe");
        }
        Optional<Prato> pratoOP = Prato.findByIdOptional(id);
        pratoOP.ifPresentOrElse(Prato::delete,()-> {throw new NotFoundException("Prato não encontrado");});

    }
}
