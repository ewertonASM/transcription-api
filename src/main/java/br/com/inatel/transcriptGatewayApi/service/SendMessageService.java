package br.com.inatel.transcriptGatewayApi.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.inatel.transcriptGatewayApi.exception.BadRequestException;
import br.com.inatel.transcriptGatewayApi.handler.ExceptionsMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class SendMessageService {

    
    private final RabbitTemplate rabbitTemplate;

    @Value("${queue.audio_extract:audio-extract}")
    String audioExtractQueue;

    public void messageSender(Object mensagem){

        try {
            this.rabbitTemplate.convertAndSend(audioExtractQueue, mensagem);
            
        } catch (Exception e) {
            log.error(ExceptionsMessage.MESSAGE_SEND_FAIL);
            throw new BadRequestException(ExceptionsMessage.MESSAGE_SEND_FAIL);
        }

    }
    
}
