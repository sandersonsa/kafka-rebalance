package xyz.sandersonsa.kafka_sp;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.checkerframework.checker.units.qual.s;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import io.confluent.parallelconsumer.ParallelConsumerOptions;
import io.confluent.parallelconsumer.ParallelConsumerOptions.ProcessingOrder;
import io.confluent.parallelconsumer.ParallelStreamProcessor;
import xyz.sandersonsa.kafka_sp.entity.Mensagem;
import xyz.sandersonsa.kafka_sp.service.MensagemService;

@SpringBootApplication
@EnableScheduling
public class KafkaSpringbootPerformanceApplication {

	private static final Logger LOG = LoggerFactory.getLogger(KafkaSpringbootPerformanceApplication.class);

	@Autowired
    private MensagemService mensagemService;

	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringbootPerformanceApplication.class, args);
	}

	// @Bean
	// public NewTopic topic() {
	// 	return TopicBuilder.name("kgh2381").partitions(1).replicas(1).build();
	// }

	// @KafkaListener(id = "kgh2381", topics = "kgh2381", autoStartup = "false")
	// void listen(String in) {
	// 	log.info(in);
	// }

	@KafkaListener(id = "kgh2381",autoStartup = "false", topics = "${app.spring.kafka.consumer.topic}", properties = {
            "max.poll.interval.ms:" + "${app.spring.kafka.max.poll.interval.ms}",
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG + "=${app.spring.kafka.max.poll.records}"
    })
    public void consumehttp(ConsumerRecord<String, String> consumerRecord) {
        String hostName = System.getenv("HOSTNAME");

        LOG.info("Partition : {}, Offset : {}, Message : {}", consumerRecord.partition(), consumerRecord.offset(),
                consumerRecord.value());
        try {
            Mensagem mensagem = new Mensagem();
            mensagem.setUuid(consumerRecord.value());
            mensagem.setHostName(hostName);
            mensagem.setPartition(String.valueOf(consumerRecord.partition()));

            mensagemService.salvarMensagem(mensagem);
            // mensagemRepository.saveAndFlush(mensagem);
        } catch (Exception e) {
            LOG.error("Error: ", e);
        }

    }

	@Bean
	ApplicationRunner runner(KafkaListenerEndpointRegistry registry, ConsumerFactory<String, String> cf,
			KafkaTemplate<String, String> template) {

		return args -> {
			MessageListener messageListener = (MessageListener) registry.getListenerContainer("kgh2381")
					.getContainerProperties().getMessageListener();
			Consumer<String, String> consumer = cf.createConsumer("group", "");
			var options = ParallelConsumerOptions.<String, String>builder()
					.ordering(ProcessingOrder.KEY)
					.consumer(consumer)
					.maxConcurrency(100)
					.build();
			ParallelStreamProcessor<String, String> processor = ParallelStreamProcessor
					.createEosStreamProcessor(options);
			processor.subscribe(List.of("t-rebalance"));
			processor.poll(context -> messageListener.onMessage(context.getSingleConsumerRecord(), null, consumer));
			// IntStream.range(0, 10).forEach(i -> template.send("kgh2381", "foo" + i));
		};
	}

	// @Bean
	// public RestTemplate pooledRestTemplate() {
	// 	PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
	// 	connectionManager.setMaxTotal(2000);
	// 	connectionManager.setDefaultMaxPerRoute(2000);

	// 	HttpClient httpClient = HttpClientBuilder.create()
	// 			.setConnectionManager(connectionManager)
	// 			.build();

	// 	return new RestTemplateBuilder().rootUri("http://service-b-base-url:8080/")
	// 			.setConnectTimeout(Duration.ofMillis(1000))
	// 			.setReadTimeout(Duration.ofMillis(1000))
	// 			.messageConverters(new StringHttpMessageConverter(), new MappingJackson2HttpMessageConverter())
	// 			.requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient))
	// 			.build();
	// }

}
