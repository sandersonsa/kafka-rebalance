package xyz.sandersonsa.kafkaproducer.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    List<String> listaRenavam = Arrays.asList("01", "03", "04");

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
        Random rand = new Random();
        for (int i = 0; i < quantidade; i++) {
            int randomIndex = rand.nextInt(listaRenavam.size());
            String renavam = listaRenavam.get(randomIndex);
            UUID uuid = UUID.randomUUID();
            JSONObject json = buildJson(uuid.toString(), renavam);
            
            //kafkaTemplate.send("cluster-sefazrj-beta.req-servicos-ipva", uuid.toString());

            var record = new ProducerRecord<String, String>("t-rebalance", json.toString());
            record.headers().add("transaction-id", "23".getBytes());

            kafkaTemplate.send(record);

        }
        System.out.println(quantidade + " mensagens enviadas");
        return CompletableFuture.completedFuture("Complete");
    }

    private JSONObject buildJson(String id, String renavam) {
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
        parteVariavel.put("renavam", renavam);
        parteVariavel.put("indicador_bloco", 1);
        
        json.put("parte_fixa", parteFixa);
        json.put("parte_variavel", parteVariavel);
        return json;
    }


}
