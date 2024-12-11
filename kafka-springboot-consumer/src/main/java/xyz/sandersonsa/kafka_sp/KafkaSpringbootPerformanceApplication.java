package xyz.sandersonsa.kafka_sp;

import java.time.Duration;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableScheduling
public class KafkaSpringbootPerformanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringbootPerformanceApplication.class, args);
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
