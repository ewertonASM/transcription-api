package br.com.inatel.transcriptGatewayApi.adapter;

import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import br.com.inatel.transcriptGatewayApi.dto.SnippetSubtitleDTO;
import br.com.inatel.transcriptGatewayApi.envs.Envs;
import br.com.inatel.transcriptGatewayApi.exception.BadRequestException;
import br.com.inatel.transcriptGatewayApi.handler.ExceptionsMessage;
import br.com.inatel.transcriptGatewayApi.mapper.SnippetSubtitleMapper;
import br.com.inatel.transcriptGatewayApi.repository.SnippetSubtitleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Component
@Log4j2
@RequiredArgsConstructor
public class TranscriptionConsumer {

    private final SnippetSubtitleRepository snippetSubtitleRepository;
    
    List<SnippetSubtitleDTO> subtitle = new LinkedList<SnippetSubtitleDTO>();
    
    @RabbitListener(queues = Envs.SUBTITLE_QUEUE)
    public void consumidor(SnippetSubtitleDTO snippetSubtitleDTO){

        try {
            log.info("Saving subtitle...");
            snippetSubtitleRepository.save(SnippetSubtitleMapper.INSTANCE.toSnippetSubtitle(snippetSubtitleDTO));
            
        } catch (Exception e) {
            log.error(ExceptionsMessage.TRANSCRIPTION_FAIL);
        }


    }
    
}
