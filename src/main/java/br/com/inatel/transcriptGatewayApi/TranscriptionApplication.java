package br.com.inatel.transcriptGatewayApi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import br.com.inatel.transcriptGatewayApi.storage.StorageProperties;
import br.com.inatel.transcriptGatewayApi.storage.StorageService;

// @Log4j2
@EnableAsync
@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class TranscriptionApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(TranscriptionApplication.class, args);
	
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

}
