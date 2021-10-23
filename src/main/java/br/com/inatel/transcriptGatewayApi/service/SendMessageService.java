package br.com.inatel.transcriptGatewayApi.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SendMessageService {

    
    private final RabbitTemplate rabbitTemplate;

    @Value("${queue.audio_extract:audio-extract}")
    String audioExtractQueue;

    public void messageSender(Object mensagem){

        this.rabbitTemplate.convertAndSend(audioExtractQueue, mensagem);
    }
    
}
