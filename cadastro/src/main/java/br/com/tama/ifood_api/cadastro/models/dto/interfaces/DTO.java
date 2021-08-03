package br.com.tama.ifood_api.cadastro.models.dto.interfaces;

import javax.validation.ConstraintValidatorContext;

public interface DTO {

    default boolean isValid(ConstraintValidatorContext constraintValidatorContext){
        return true;
    }
}
