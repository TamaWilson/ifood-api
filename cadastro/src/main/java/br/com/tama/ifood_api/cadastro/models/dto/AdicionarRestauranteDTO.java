package br.com.tama.ifood_api.cadastro.models.dto;

import br.com.tama.ifood_api.cadastro.models.dto.annotations.ValidDTO;
import br.com.tama.ifood_api.cadastro.models.dto.interfaces.DTO;
import br.com.tama.ifood_api.cadastro.models.entities.Restaurante;

import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ValidDTO
public class AdicionarRestauranteDTO implements DTO {

    @NotNull
    @Pattern(regexp = "[0-9]{2}\\.[0-9]{3}\\.[0-9]{3}\\/[0-9]{4}\\-[0-9]{2}")
    public String cnpj;

    @Size(min=3, max=30)
    public String nomeFantasia;

    public LocalizacaoDTO localizacao;

    @Override
    public boolean isValid(ConstraintValidatorContext constraintValidatorContext){
        constraintValidatorContext.disableDefaultConstraintViolation();
        if(Restaurante.find("cnpj", cnpj).count() > 0){
            constraintValidatorContext.buildConstraintViolationWithTemplate("CNPJ já cadastrado")
                    .addPropertyNode("cnpj")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
