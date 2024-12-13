package xyz.sandersonsa.kafka_sp.service;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import xyz.sandersonsa.kafka_sp.entity.Mensagem;

@Service
public class MensagemService {

    private static final Logger LOG = LoggerFactory.getLogger(MensagemService.class);

    @Value("${app.rest.uri}")
    private String REST_URI;

    CloseableHttpClient httpClient;

    public MensagemService() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(2000);
        connectionManager.setDefaultMaxPerRoute(2000);

        httpClient = HttpClientBuilder.create()
                .setConnectionManager(connectionManager)
                .setConnectionManagerShared(true)
                .build();
    }

    public void salvarMensagemHttp(Mensagem mensagem) throws ParseException, IOException {        
        final HttpPost httpPost = new HttpPost(REST_URI);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(mensagem);
        final StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        try (CloseableHttpClient client = httpClient;
            CloseableHttpResponse response = httpClient.execute(httpPost)){

            String result = EntityUtils.toString(response.getEntity());
            System.out.println("POST Response Status:: " + response.getStatusLine().getStatusCode());
            // LOG.info(" ## Result: {}", result);
            // assertThat(statusCode, equalTo(HttpStatus.SC_OK));
        }

    }

    public void testePerformance(Mensagem mensagem) throws ParseException, IOException {        
        LOG.info("ParallelStreamProcessor - Message : {}", mensagem.getUuid());
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            HttpGet request = new HttpGet("http://rest-api-apps.apps.cluster-kp9rz.kp9rz.sandbox423.opentlc.com/api/v1/teste/v2");
            // add request headers
            request.addHeader("custom-key", "sandersonsa");
            CloseableHttpResponse response = httpClient.execute(request);
            try {
                // Get HttpResponse Status
                // System.out.println(response.getProtocolVersion());              // HTTP/1.1
                // System.out.println(response.getStatusLine().getStatusCode());   // 200
                // System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                // System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                // HttpEntity entity = response.getEntity();
                // if (entity != null) {
                //     // return it as a String
                //     String result = EntityUtils.toString(entity);
                //     System.out.println(result);
                // }
            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }

    }

}
