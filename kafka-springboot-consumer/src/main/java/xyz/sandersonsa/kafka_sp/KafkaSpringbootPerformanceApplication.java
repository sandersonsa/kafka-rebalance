package xyz.sandersonsa.kafka_sp;

import java.util.List;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListener;
import io.confluent.parallelconsumer.ParallelConsumerOptions;
import io.confluent.parallelconsumer.ParallelConsumerOptions.ProcessingOrder;
import io.confluent.parallelconsumer.ParallelStreamProcessor;
import xyz.sandersonsa.kafka_sp.entity.Mensagem;
import xyz.sandersonsa.kafka_sp.service.MensagemService;

@SpringBootApplication
public class KafkaSpringbootPerformanceApplication {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaSpringbootPerformanceApplication.class);

	@Autowired
    private MensagemService mensagemService;

	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringbootPerformanceApplication.class, args);
	}

	@KafkaListener(id = "confluent.parallel.consumer",autoStartup = "false", topics = "${app.spring.kafka.consumer.topic}", properties = {
            "max.poll.interval.ms:" + "${app.spring.kafka.max.poll.interval.ms}",
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG + "=${app.spring.kafka.max.poll.records}"
    })
    public void consumehttp(ConsumerRecord<String, String> consumerRecord) {
        String hostName = System.getenv("HOSTNAME");

        LOG.info("ParallelStreamProcessor - Partition : {}, Offset : {}, Message : {}", consumerRecord.partition(), consumerRecord.offset(),
                consumerRecord.value());
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

	@Bean
	ApplicationRunner runner(KafkaListenerEndpointRegistry registry, ConsumerFactory<String, String> cf,
			KafkaTemplate<String, String> template) {

		return args -> {
			MessageListener messageListener = (MessageListener) registry.getListenerContainer("confluent.parallel.consumer")
					.getContainerProperties().getMessageListener();
			Consumer<String, String> consumer = cf.createConsumer("confluent.parallel.consumer", "");
			var options = ParallelConsumerOptions.<String, String>builder()
					.ordering(ProcessingOrder.KEY)
					.consumer(consumer)
					.maxConcurrency(100)
					.build();
			ParallelStreamProcessor<String, String> processor = ParallelStreamProcessor
					.createEosStreamProcessor(options);
			processor.subscribe(List.of("t-rebalance"));
			processor.poll(record ->
        		LOG.info("Concurrently processing a record: {}", record));
			// processor.poll(context -> messageListener.onMessage(context.getSingleConsumerRecord(), null, consumer));
			// IntStream.range(0, 10).forEach(i -> template.send("kgh2381", "foo" + i));
		};
	}

}
