package xyz.sandersonsa.kafka_sp.service;

import java.io.IOException;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.sandersonsa.kafka_sp.entity.Mensagem;

@Service
public class MensagemService {

    private static final Logger LOG = LoggerFactory.getLogger(MensagemService.class);

    String uri = "http://rest-api-apps.apps.cluster-kp9rz.kp9rz.sandbox423.opentlc.com/api/v1/mensagem";
    HttpClient httpClient;

    public MensagemService() {
        LOG.info("Inicializando MensagemService");
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(2000);
        connectionManager.setDefaultMaxPerRoute(2000);

        httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .build();
    }

    public void salvarMensagemHttp(Mensagem mensagem) throws ParseException, IOException {

        try {
            HttpPost httpPost = new HttpPost(uri);

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(mensagem);
            final StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // Executing the Get request
            httpClient.execute(httpPost);

            // Getting the response
            // String response = EntityUtils.toString(httpresponse.getEntity());
            // System.out.println(response);
        } catch (Exception e) {
            LOG.error(" ### => Error: ", e);
        }

    }

}
