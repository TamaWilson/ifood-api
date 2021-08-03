package br.com.tama.ifood_api.cadastro.infra.exceptions.mapper;

import br.com.tama.ifood_api.cadastro.infra.exceptions.dto.ConstraintValidationReponse;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST).entity(ConstraintValidationReponse.of(exception)).build();
    }
}
