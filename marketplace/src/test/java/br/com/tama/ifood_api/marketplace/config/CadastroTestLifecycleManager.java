package br.com.tama.ifood_api.marketplace.config;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.ws.rs.POST;
import java.util.HashMap;
import java.util.Map;

public class CadastroTestLifecycleManager implements QuarkusTestResourceLifecycleManager {

    public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:12.2");

    @Override
    public Map<String, String> start() {
        POSTGRES.start();
        Map<String, String> propriedades = new HashMap<>();

        //Não encontrei uma prop para pegar a URL e a porta sem o prefixo "jdbc"
        String rawUrl = (POSTGRES.getJdbcUrl().split("jdbc:"))[1]; //.split("\\?"))[0];

        propriedades.put("quarkus.datasource.jdbc.url", POSTGRES.getJdbcUrl());
        propriedades.put("quarkus.datasource.reactive.url", rawUrl);
        propriedades.put("quarkus.datasource.username", POSTGRES.getUsername());
        propriedades.put("quarkus.datasource.password", POSTGRES.getPassword());

        return propriedades;
    }

    @Override
    public void stop() {
        if(POSTGRES.isRunning()){
            POSTGRES.stop();
        }
    }
}

