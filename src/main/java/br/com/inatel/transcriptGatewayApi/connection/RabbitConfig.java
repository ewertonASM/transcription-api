package br.com.inatel.transcriptGatewayApi.connection;

import org.springframework.context.annotation.Configuration;

import br.com.inatel.transcriptGatewayApi.exception.BadRequestException;
import br.com.inatel.transcriptGatewayApi.handler.ExceptionsMessage;
import lombok.extern.log4j.Log4j2;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;

@Log4j2
@Configuration
public class RabbitConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {

        try {

            log.debug("Creating rabbit template...");
            
            final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
            rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
            
            log.debug("Rabbit template created.");

            return rabbitTemplate;

            
        } catch (Exception e) {
            throw new BadRequestException(ExceptionsMessage.RABBIT_SETUP_FAIL);
        }
        
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {

        try {
            return new Jackson2JsonMessageConverter();
        } catch (Exception e) {
            throw new BadRequestException(ExceptionsMessage.RABBIT_SETUP_FAIL);
        }
    }
    
}
