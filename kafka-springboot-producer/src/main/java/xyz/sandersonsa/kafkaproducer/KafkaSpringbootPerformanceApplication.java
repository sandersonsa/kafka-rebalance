package xyz.sandersonsa.kafkaproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class KafkaSpringbootPerformanceApplication {


	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringbootPerformanceApplication.class, args);
	}

}
