package br.com.inatel.transcriptGatewayApi.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void messageSender(String queueName, Object mensagem){

        this.rabbitTemplate.convertAndSend(queueName, mensagem);
    }
    
}
