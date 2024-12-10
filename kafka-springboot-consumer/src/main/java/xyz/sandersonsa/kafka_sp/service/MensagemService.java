package xyz.sandersonsa.kafka_sp.service;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import xyz.sandersonsa.kafka_sp.entity.Mensagem;

@Service
public class MensagemService {

    public void salvarMensagem(Mensagem mensagem) {
        // Lógica para salvar a mensagem no banco de dados
        
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://rest-api-apps.apps.cluster-kp9rz.kp9rz.sandbox423.opentlc.com/api/v1/mensagem"; // or any other uri
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Mensagem> request = new HttpEntity<>(mensagem, headers);
        restTemplate.postForObject(uri, request, Mensagem.class);

        // HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        // ResponseEntity<?> result =
        // restTemplate.exchange(uri, HttpMethod.GET, entity, returnClass);
    }

}
