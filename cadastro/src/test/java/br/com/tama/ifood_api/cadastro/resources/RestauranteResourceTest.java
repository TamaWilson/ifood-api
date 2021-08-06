package br.com.tama.ifood_api.cadastro.resources;

import br.com.tama.ifood_api.cadastro.config.CadastroTestLifecycleManager;
import br.com.tama.ifood_api.cadastro.models.entities.Restaurante;
import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;

@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifecycleManager.class)
@TestSecurity(user = "proprietario", roles = "proprietario")
class RestauranteResourceTest {


    @Test
    @DataSet("restaurantes-cenario-1.yml")
    void testBuscarRestaurantes(){
        String resultado = given()
                .contentType(ContentType.JSON)
                .when().get("/restaurantes")
                .then()
                .statusCode(200)
                .extract().asString();
       Approvals.verifyJson(resultado);
    }

    @Test
    @DataSet("restaurantes-cenario-1.yml")
    void testAlterarRestaurante(){
        Restaurante dto = new Restaurante();
        dto.nome = "novoNome";
        Long parameterValue = 123L;
        given()
                .contentType(ContentType.JSON)
                .with().pathParam("id", parameterValue)
                .body(dto)
                .when().put("/restaurantes/{id}")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode())
                .extract().asString();

        Restaurante findById = Restaurante.findById(parameterValue);
    }
}
