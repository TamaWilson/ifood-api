package br.com.tama.ifood_api.marketplace.resources;

import br.com.tama.ifood_api.marketplace.config.CadastroTestLifecycleManager;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@QuarkusTestResource(CadastroTestLifecycleManager.class)
class PratoResourceTest {

    @Test
    void testPratonsEndpoint(){
        String body = given()
                .when().get("/pratos")
                .then()
                .statusCode(200).extract().asString();

        System.out.println(body);
    }
}
