package br.com.tama.ifood_api.pedido.config;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.io.IOException;

@ApplicationScoped
public class ESService {

    private RestHighLevelClient client;

    void startup(@Observes StartupEvent startupEvent) {
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        );
    }

    void shutdown(@Observes ShutdownEvent shutdownEvent){
        try{
            client.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void index(String index, String json){
        IndexRequest indexRequest = new IndexRequest(index).source(json, XContentType.JSON);
        try {
            client.index(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
