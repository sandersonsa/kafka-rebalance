package xyz.sandersonsa.kafka_sp;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import xyz.sandersonsa.kafka_sp.entity.Mensagem;
import xyz.sandersonsa.kafka_sp.service.MensagemService;

@Service
public class RebalanceConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(RebalanceConsumer.class);

    @Autowired
    private MensagemService mensagemService;

    @Value("${app.spring.kafka.consumer.delay.ms}")
    private String consumerDalay;

    @KafkaListener(topics = "${app.spring.kafka.consumer.topic}", groupId = "${app.spring.kafka.consumer.group-id.tradicional}", properties = {
            "max.poll.interval.ms:" + "${app.spring.kafka.max.poll.interval.ms}",
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG + "=${app.spring.kafka.max.poll.records}"
    })
    public void consumehttp(ConsumerRecord<String, String> consumerRecord) {
        String hostName = System.getenv("HOSTNAME");

        // LOG.info("Partition : {}, Offset : {}, Message : {}", consumerRecord.partition(), consumerRecord.offset(), consumerRecord.value());
        try {
            Mensagem mensagem = new Mensagem();
            mensagem.setUuid(consumerRecord.value());
            mensagem.setHostName(hostName);
            mensagem.setPartition(String.valueOf(consumerRecord.partition()));

            mensagemService.salvarMensagemHttp(mensagem);
        } catch (Exception e) {
            LOG.error("Error: ", e);
        }

    }
}