package br.com.inatel.transcriptGatewayApi.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.inatel.transcriptGatewayApi.dto.SnippetSubtitleDTO;
import br.com.inatel.transcriptGatewayApi.envs.Envs;
import br.com.inatel.transcriptGatewayApi.mapper.SnippetSubtitleMapper;
import br.com.inatel.transcriptGatewayApi.repository.SnippetSubtitleRepository;
import io.netty.util.Constant;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import br.com.inatel.transcriptGatewayApi.dto.AudioForTranscriptionDTO;

import java.util.LinkedList;
import java.util.List;


@Component
@Log4j2
@RequiredArgsConstructor
public class Consumer {

    private final SnippetSubtitleRepository snippetSubtitleRepository;
    List<SnippetSubtitleDTO> subtitle = new LinkedList<SnippetSubtitleDTO>();
    
    @RabbitListener(queues = Envs.SUBTITLE_QUEUE)
    public void consumidor(SnippetSubtitleDTO snippetSubtitleDTO){

        log.info("Saving subtitle...");
        snippetSubtitleRepository.save(SnippetSubtitleMapper.INSTANCE.toSnippetSubtitle(snippetSubtitleDTO));

    }
    
}
