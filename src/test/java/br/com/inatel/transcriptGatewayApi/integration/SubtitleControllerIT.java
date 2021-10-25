package br.com.inatel.transcriptGatewayApi.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import br.com.inatel.transcriptGatewayApi.model.SnippetSubtitle;
import br.com.inatel.transcriptGatewayApi.repository.SnippetSubtitleRepository;
import br.com.inatel.transcriptGatewayApi.util.SnippetSubtitleCreator;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SubtitleControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;
    
    @Autowired
    private SnippetSubtitleRepository snippetSubtitleRepository;

    @Test
    @DisplayName("findByStockId returns stock quote when successful")
    void findByStockId_ReturnsStock_WhenSuccessful(){

        SnippetSubtitle snippetSubtitleToSave = SnippetSubtitleCreator.createSnippetSubtitleToBeSaved();
        SnippetSubtitle snippetSubtitle = snippetSubtitleRepository.save(snippetSubtitleToSave);

        String expectedId = snippetSubtitle.getId();

       testRestTemplate.getForEntity("/videos/{id}/subtitle", ResponseEntity.class, expectedId);

       Assertions.assertThat(snippetSubtitleToSave.getId()).isNotNull().isEqualTo(expectedId);

    }


}
