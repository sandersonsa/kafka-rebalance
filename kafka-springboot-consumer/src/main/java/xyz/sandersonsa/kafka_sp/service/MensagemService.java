package xyz.sandersonsa.kafka_sp.service;

import java.io.IOException;
import java.util.Collections;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

        HttpPost httpPost = new HttpPost(uri);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(mensagem);
        final StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        //Executing the Get request
        httpClient.execute(httpPost);

        //Getting the response
        // String response = EntityUtils.toString(httpresponse.getEntity());
        // System.out.println(response);
        
    }

}
