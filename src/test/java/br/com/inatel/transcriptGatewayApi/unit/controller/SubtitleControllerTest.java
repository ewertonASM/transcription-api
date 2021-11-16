package br.com.inatel.transcriptGatewayApi.unit.controller;
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

import br.com.inatel.transcriptGatewayApi.controller.SubtitleController;
import br.com.inatel.transcriptGatewayApi.service.StorageService;
import br.com.inatel.transcriptGatewayApi.service.SubtitleService;
import br.com.inatel.transcriptGatewayApi.util.SubtitleCreator;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMetrics
class SubtitleControllerTest {

    private static final UUID VIDEO_ID = UUID.randomUUID();

    private static final String subtitleTestPath = "/home/emoura/Workspace/Inatel/Video-translator/Video-api/transcription-api/src/test/java/br/com/inatel/transcriptGatewayApi/test-data/9ab7d462-ba0b-4da6-9726-b88a9a45d3cf.srt";
    
    @Mock
    private SubtitleService subtitleServiceMock;
    
    @Mock
    private StorageService storageServiceMock;
    
	@InjectMocks
	private SubtitleController subtitleController;

    @BeforeEach
    void setUp(){


        BDDMockito.when(subtitleServiceMock.findSubtitle(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
                .thenReturn(subtitleTestPath);

        BDDMockito.when(subtitleServiceMock.downloadResource(subtitleTestPath))
                .thenReturn(SubtitleCreator.createValidSubtitle());

    }

    @Test
    @DisplayName("findSubtitle returns response entity with subtitle file when successful")
    void findSubtitle_ReturnsResponseEntityWithSubtitleFile_WhenSuccessful(){

        Optional<String> lang = Optional.of("pt");

        ResponseEntity<InputStreamResource> response = subtitleController.findSubtitle(VIDEO_ID, lang);

        Assertions.assertThat(response.getStatusCode())
                .isEqualTo(HttpStatus.OK);
                
        Assertions.assertThat(response.getHeaders().getContentLength())
                        .isEqualTo(SubtitleCreator.createValidSubtitle().getHeaders().getContentLength());

    }

}