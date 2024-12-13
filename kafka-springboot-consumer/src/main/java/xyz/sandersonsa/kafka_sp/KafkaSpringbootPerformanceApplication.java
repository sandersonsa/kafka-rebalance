package xyz.sandersonsa.kafka_sp;

import java.io.IOException;
import java.util.List;

import org.apache.http.ParseException;
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

	@KafkaListener(id = "confluent.parallel.consumer", autoStartup = "false", topics = "${app.spring.kafka.consumer.topic}", properties = {
			"max.poll.interval.ms:" + "${app.spring.kafka.max.poll.interval.ms}",
			ConsumerConfig.MAX_POLL_RECORDS_CONFIG + "=${app.spring.kafka.max.poll.records}"
	})
	public void consumehttp(ConsumerRecord<String, String> consumerRecord) {
		//ativo no ApplicationRunner
	}

	private void salvarMensagem(ConsumerRecord<String, String> consumerRecord) {
		String hostName = System.getenv("HOSTNAME");
		// LOG.info("ParallelStreamProcessor - Partition : {}, Offset : {}, Message : {}", consumerRecord.partition(), consumerRecord.offset(), consumerRecord.value());
		Mensagem mensagem = new Mensagem();
		mensagem.setUuid(consumerRecord.value());
		mensagem.setHostName(hostName);
		mensagem.setPartition(String.valueOf(consumerRecord.partition()));

		try {
			mensagemService.testePerformance(mensagem);
			// mensagemService.salvarMensagemHttp(mensagem);
		} catch (ParseException | IOException e) {
			LOG.error(" ### ERROR salvarMensagemHttp => {} ", mensagem.getUuid());
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
					.maxConcurrency(30)
					.build();
			ParallelStreamProcessor<String, String> processor = ParallelStreamProcessor
					.createEosStreamProcessor(options);
			processor.subscribe(List.of("t-rebalance"));
			processor.poll(record ->
				salvarMensagem(record.getSingleConsumerRecord())
			);
		};
	}

}

// consumerRecord=ConsumerRecord(
	// 	topic = t-rebalance, 
	// 	partition = 6, 
	// 	leaderEpoch = 13, 
	// 	offset = 945170, 
	// 	CreateTime = 1733924275610, 
	// 	serialized key size = -1, 
	// 	serialized value size = 36, 
	// 	headers = RecordHeaders(
	// 		headers = [],
	// 		isReadOnly = false), 
	// 	key = null, 
	// 	value = 5083e600-878a-4b63-a58f-e0cc2cecb07b)))]
	// 	}
