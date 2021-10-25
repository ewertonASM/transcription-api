package br.com.inatel.transcriptGatewayApi.service;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.inatel.transcriptGatewayApi.envs.Envs;
import br.com.inatel.transcriptGatewayApi.exception.BadRequestException;
import br.com.inatel.transcriptGatewayApi.model.SnippetSubtitle;
import br.com.inatel.transcriptGatewayApi.repository.SnippetSubtitleRepository;
import br.com.inatel.transcriptGatewayApi.util.SnippetSubtitleCreator;

@ExtendWith(SpringExtension.class)
public class SubtitleServiceTest {

    @InjectMocks
    private SubtitleService snippetSubtitleService;

    @Mock
    private SnippetSubtitleRepository snippetSubtitleRepositoryMock;

    @BeforeEach
    void setUp(){


        BDDMockito.when(snippetSubtitleRepositoryMock.findAll())
                .thenReturn(List.of(SnippetSubtitleCreator.createSnippetSubtitle()));

        BDDMockito.when(snippetSubtitleRepositoryMock.findById(ArgumentMatchers.anyString()).get())
                .thenReturn(SnippetSubtitleCreator.createSnippetSubtitle());

        BDDMockito.when(snippetSubtitleRepositoryMock.save(ArgumentMatchers.any(SnippetSubtitle.class)))
                .thenReturn(SnippetSubtitleCreator.createSnippetSubtitle());

    }
    
  
    @Test
    @DisplayName("findById returns SnippetSubtitle when successful")
    void findById_ReturnsSubtitle_WhenSuccessful() throws IOException{
        SnippetSubtitle snippetSubtitle = SnippetSubtitleCreator.createSnippetSubtitle();

        String path = snippetSubtitleService.findSubtitle(snippetSubtitle.getVideoId(),"pt");

        Assertions.assertThat(path).isNotNull().isEqualTo(Envs.TEMP_DIR+"/"+snippetSubtitle.getVideoId()+"."+Envs.AUDIO_EXT);
    }

    @Test
    @DisplayName("findById throws BadRequestException when SnippetSubtitle is not found")
    void findById_ThrowsBadRequestException_WhenSubtitleIsNotFound(){
        BDDMockito.when(snippetSubtitleRepositoryMock.findById(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> snippetSubtitleService.findSubtitle("fgt7f7", "pt"));
    }


}
