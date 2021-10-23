package br.com.inatel.transcriptGatewayApi.connection;

import javax.annotation.PostConstruct;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class RabbitMQConnection {

    @Value("${queue.exchange:amp.direct}")
    String exchangeName;

    @Value("${queue.subtitle:subtitle-receive}")
    String subtitleQueue;

    @Value("${queue.audio_extract:audio-extract}")
    String audioExtractQueue;

    private AmqpAdmin amqpAdmin;


    public RabbitMQConnection(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String queueName){
        return new Queue(queueName, false, false, false);
    }

    private DirectExchange directExchange(){
        return new DirectExchange(exchangeName);
    }

    private Binding relationship(Queue queue, DirectExchange directExchange){
       return new Binding(queue.getName(), Binding.DestinationType.QUEUE, directExchange.getName(), queue.getName(), null);
    }
    
    @PostConstruct
    private void add(){

        Queue queueAudioExtract = this.queue(audioExtractQueue);
        Queue queueSubtitleReceive = this.queue(subtitleQueue);

        DirectExchange exchange = this.directExchange();

        Binding bindingAudioExtract = this.relationship(queueAudioExtract, exchange);
        Binding bindingSubtitleReceive = this.relationship(queueSubtitleReceive, exchange);

            this.amqpAdmin.declareQueue(queueAudioExtract);
            this.amqpAdmin.declareQueue(queueSubtitleReceive);
            this.amqpAdmin.declareExchange(exchange);
            this.amqpAdmin.declareBinding(bindingAudioExtract);
            this.amqpAdmin.declareBinding(bindingSubtitleReceive);



    }
    
}
