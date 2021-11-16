package br.com.inatel.transcriptGatewayApi.unit.service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.actuate.metrics.AutoConfigureMetrics;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import br.com.inatel.transcriptGatewayApi.adapter.TranslatorAdapter;
import br.com.inatel.transcriptGatewayApi.envs.Envs;
import br.com.inatel.transcriptGatewayApi.model.SnippetSubtitle;
import br.com.inatel.transcriptGatewayApi.repository.SnippetSubtitleRepository;
// import br.com.inatel.transcriptGatewayApi.controller.SubtitleController;
import br.com.inatel.transcriptGatewayApi.service.StorageService;
import br.com.inatel.transcriptGatewayApi.service.SubtitleService;
import br.com.inatel.transcriptGatewayApi.util.SnippetSubtitleCreator;
import br.com.inatel.transcriptGatewayApi.util.SubtitleCreator;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMetrics
class SubtitleControllerTest {

    private static final UUID VIDEO_ID = UUID.randomUUID();

    private static final String subtitleTestPath = "/home/emoura/Workspace/Inatel/Video-translator/Video-api/transcription-api/src/test/java/br/com/inatel/transcriptGatewayApi/test-data/9ab7d462-ba0b-4da6-9726-b88a9a45d3cf.srt";
    
    @InjectMocks
    private SubtitleService subtitleService;

    @Mock
    private SnippetSubtitleRepository snippetSubtitleRepositoryMock;


    @BeforeEach
    void setUp(){


        BDDMockito.when(snippetSubtitleRepositoryMock.save(ArgumentMatchers.any(SnippetSubtitle.class)))
                .thenReturn(SnippetSubtitleCreator.createSnippetSubtitle());

        BDDMockito.when(snippetSubtitleRepositoryMock.findByVideoId(ArgumentMatchers.anyString()))
                .thenReturn(List.of(SnippetSubtitleCreator.createSnippetSubtitle()));

      
    }

    @Test
    @DisplayName("findSubtitle returns response entity with subtitle file when successful")
    void findSubtitle_ReturnsResponseEntityWithSubtitleFile_WhenSuccessful(){

        Optional<String> lang = Optional.of("pt");

        String expectedPath = Envs.TEMP_DIR + "/" + VIDEO_ID.toString() + Envs.SUBTITLE_EXT;

        String response = subtitleService.findSubtitle(VIDEO_ID.toString(), lang.get());


        Assertions.assertThat(response)
                .isEqualTo(expectedPath);
                
    }

}