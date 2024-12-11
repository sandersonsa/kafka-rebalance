package xyz.sandersonsa.kafka_sp.service;

import java.io.IOException;
import java.util.Collections;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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

    String uri = "http://rest-api-apps.apps.cluster-kp9rz.kp9rz.sandbox423.opentlc.com/api/v1/mensagem";

    public void salvarMensagem(Mensagem mensagem) {
        
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Mensagem> request = new HttpEntity<>(mensagem, headers);
        restTemplate.postForObject(uri, request, Mensagem.class);
    }

    public void salvarMensagemHttp(Mensagem mensagem) throws ParseException, IOException {
        final HttpPost httpPost = new HttpPost(uri);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(mensagem);
        final StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(httpPost)) {
        }
    }

}
