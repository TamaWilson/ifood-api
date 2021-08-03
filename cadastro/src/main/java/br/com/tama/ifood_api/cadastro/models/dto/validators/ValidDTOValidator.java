package br.com.tama.ifood_api.cadastro.models.dto.validators;

import br.com.tama.ifood_api.cadastro.models.dto.annotations.ValidDTO;
import br.com.tama.ifood_api.cadastro.models.dto.interfaces.DTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidDTOValidator implements ConstraintValidator<ValidDTO, DTO> {

    @Override
    public void initialize(ValidDTO constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(DTO dto, ConstraintValidatorContext constraintValidatorContext) {
        return dto.isValid(constraintValidatorContext);
    }
}
