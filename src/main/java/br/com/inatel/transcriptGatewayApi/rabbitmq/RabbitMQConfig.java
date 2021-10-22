// package br.com.inatel.transcriptGatewayApi.rabbitmq;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.Profile;

// import lombok.extern.log4j.Log4j2;

// import org.springframework.amqp.core.Queue;
// import org.springframework.amqp.rabbit.connection.ConnectionFactory;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
// import org.springframework.context.annotation.Bean;

// @Profile({"tut1","hello"})
// @Configuration
// @Log4j2
// public class RabbitMQConfig {


//     @Bean
// 	public Queue hello() {
// 		return new Queue("hello");
// 	}

// 	// @Profile("hello")
// 	// @Bean
// 	// public SubtitleReceiver receiver() {
// 	// 	return new SubtitleReceiver();
// 	// }

// 	@Profile("hello")
// 	@Bean
// 	public AudioSender sender() {
//         log.info("*********************************ENVIADO MENSAGEMMMMMMM");
//         AudioSender audioSender = new AudioSender();
//         audioSender.send();
// 		return audioSender;
// 	}

//     @Bean
//     public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
//         final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//         rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
//         return rabbitTemplate;
//     }

//     @Bean
//     public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
//         return new Jackson2JsonMessageConverter();
//     }
    
// }
