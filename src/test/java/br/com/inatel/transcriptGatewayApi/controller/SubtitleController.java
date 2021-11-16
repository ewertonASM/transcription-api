package br.com.inatel.transcriptGatewayApi.controller;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import br.com.inatel.transcriptGatewayApi.model.SnippetSubtitle;
import br.com.inatel.transcriptGatewayApi.repository.SnippetSubtitleRepository;
import br.com.inatel.transcriptGatewayApi.util.SnippetSubtitleCreator;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SubtitleController {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private SnippetSubtitleRepository snippetSubtitleRepository;

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("DownloadSubtitle returns subtitle when successful")
    public void DownloadSubtitle() {

        SnippetSubtitle snippetSubtitle = SnippetSubtitleCreator.createSnippetSubtitleToBeSaved();
        snippetSubtitleRepository.save(snippetSubtitle);

        String url = "/videos/"+snippetSubtitle.getVideoId()+"/subtitle";

        ResponseEntity<byte[]> response = testRestTemplate.getForEntity(url, byte[].class);
        
        Assertions.assertThat(response.getBody()).isNotEmpty();
       
    }

    @Test
    @DisplayName("DownloadSubtitleWithInvalidID returns exception when successful")
    public void DownloadSubtitleWithInvalidID() {

        String notUUID = "not-is-uuid";

        String url = "/videos/"+notUUID+"/subtitle";

        ResponseEntity<byte[]> response = testRestTemplate.getForEntity(url, byte[].class);
        
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
     
    }

    @Test
    @DisplayName("DownloadNotFoundSubtitle returns exception when successful")
    public void DownloadNotFoundSubtitle() {

        SnippetSubtitle snippetSubtitle = SnippetSubtitleCreator.createSnippetSubtitleToBeSaved();

        String url = "/videos/"+snippetSubtitle.getVideoId()+"/subtitle";

        ResponseEntity<byte[]> response = testRestTemplate.getForEntity(url, byte[].class);
        
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
       
    }

    @Test
    @DisplayName("DownloadUnprocessedSubtitle returns exception when successful")
    public void DownloadUnprocessedSubtitle() {

        SnippetSubtitle snippetSubtitle = SnippetSubtitleCreator.createSnippetSubtitleToBeSaved();
        snippetSubtitle.setSnippet("1/10");
        snippetSubtitleRepository.save(snippetSubtitle);
        
        String url = "/videos/"+snippetSubtitle.getVideoId()+"/subtitle";
        
        ResponseEntity<byte[]> response = testRestTemplate.getForEntity(url, byte[].class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
       
    }

    
}

