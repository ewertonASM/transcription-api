// package br.com.inatel.transcriptGatewayApi.rabbitmq;

// import org.springframework.amqp.core.Queue;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.scheduling.annotation.Scheduled;

// import lombok.extern.log4j.Log4j2;

// /**
//  * @author Gary Russell
//  * @author Scott Deeg
//  */
// @Log4j2
// public class AudioSender {

// 	@Autowired
// 	private RabbitTemplate template;

// 	@Autowired
// 	private Queue queue;
	
// 	// @Scheduled(fixedDelay = 1000, initialDelay = 500)
// 	public void send() {
// 		String message = "Hello World!";
// 		this.template.convertAndSend(queue.getName(), message);
// 		log.info(" [x] Sent '" + message + "'");
// 	}

// }