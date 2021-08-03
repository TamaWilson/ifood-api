package br.com.tama.ifood_api.cadastro.infra.exceptions.dto;


import br.com.tama.ifood_api.cadastro.infra.exceptions.ConstraintViolationImpl;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class ConstraintValidationReponse {

    private final List<ConstraintViolationImpl> violacoes = new ArrayList<>();

    public ConstraintValidationReponse(ConstraintViolationException exception) {
        exception.getConstraintViolations().forEach(violation -> violacoes.add(ConstraintViolationImpl.of(violation)));
    }

    public static ConstraintValidationReponse of(ConstraintViolationException exception){
        return new ConstraintValidationReponse(exception);
    }

    public List<ConstraintViolationImpl> getViolacoes(){
        return violacoes;
    }
}
