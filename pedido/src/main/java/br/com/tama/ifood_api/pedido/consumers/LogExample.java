package br.com.tama.ifood_api.pedido.consumers;

import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.time.LocalDateTime;

@Path("/log")
public class LogExample {

    private static final Logger LOG = Logger.getLogger(LogExample.class);

    @GET
    public String gerarLog(){
        LOG.info("Exemplo de LOG INFO - QUARKUS");
        LOG.infov("TIMELOG {0} - QUARKUS", LocalDateTime.now());
        return "ok";
    }

}
