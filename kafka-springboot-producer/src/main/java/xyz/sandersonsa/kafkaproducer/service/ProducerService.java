package xyz.sandersonsa.kafkaproducer.service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Async
    public CompletableFuture<String> sendMessages(Integer quantidade) throws InterruptedException {                
        for (int i = 0; i < quantidade; i++) {
            UUID uuid = UUID.randomUUID();
            kafkaTemplate.send("t-rebalance", uuid.toString());
        }
        System.out.println(quantidade + " mensagens enviadas");
        return CompletableFuture.completedFuture("Complete");
    }

    @Async
    public CompletableFuture<String> sendMessagesJson(Integer quantidade) throws InterruptedException {                
        for (int i = 0; i < quantidade; i++) {
            UUID uuid = UUID.randomUUID();
            JSONObject json = buildJson(uuid.toString());
            kafkaTemplate.send("t-rebalance", json);
        }
        System.out.println(quantidade + " mensagens enviadas");
        return CompletableFuture.completedFuture("Complete");
    }

    /*
     * {
    "guid": "55a4469d-3455-4d9f-92f7-770ca4c70978",
    "parte_fixa": {
        "sequencial": "003002",
        "modalidade": 4,
        "codigo_transacao": "023",
        "uf_origem_transacao": "SF",
        "uf_origem_transmissao": "SF",
        "uf_destino_transmissao": "RJ",
        "tipo_condicionalidade": 1,
        "tamanho_transacao": "0011",
        "codigo_retorno_transacao": "00",
        "dia_juliano": "346"
    },
    "parte_variavel": {
        "renavam": "01375042146",
        "indicador_bloco": 1
    }
}
     */

    private JSONObject buildJson(String id) {
        JSONObject json = new JSONObject();
        
        json.put("guid", id);
        
        JSONObject parteFixa = new JSONObject();
        parteFixa.put("sequencial", "003002");
        parteFixa.put("modalidade", 4);
        parteFixa.put("codigo_transacao", "023");
        parteFixa.put("uf_origem_transacao", "SF");
        parteFixa.put("uf_origem_transmissao", "SF");
        parteFixa.put("uf_destino_transmissao", "RJ");
        parteFixa.put("tipo_condicionalidade", 1);
        parteFixa.put("tamanho_transacao", "0011");
        parteFixa.put("codigo_retorno_transacao", "00");
        parteFixa.put("dia_juliano", "346");
        json.put("parte_fixa", parteFixa);
        
        JSONObject parteVariavel = new JSONObject();
        parteVariavel.put("renavam", "01375042146");
        parteVariavel.put("indicador_bloco", 1);
        
        json.put("parte_fixa", parteFixa);
        json.put("parte_variavel", parteVariavel);
        return json;
    }


}
