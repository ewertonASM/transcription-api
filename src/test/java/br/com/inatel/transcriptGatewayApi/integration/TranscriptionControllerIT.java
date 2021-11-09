package br.com.inatel.transcriptGatewayApi.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.inatel.transcriptGatewayApi.dto.VideoIdDTO;
import br.com.inatel.transcriptGatewayApi.handler.ExceptionsMessage;
import br.com.inatel.transcriptGatewayApi.util.ExceptionTestDTO;
import br.com.inatel.transcriptGatewayApi.util.FileCreator;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TranscriptionControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("SendValidFileToTranscript returns video id when successful")
    public void SendValidFileToTranscript() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", FileCreator.createValidFileToBeTranscripted());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        VideoIdDTO response = testRestTemplate.postForEntity("/", requestEntity, VideoIdDTO.class).getBody();

        Assertions.assertThat(response.getVideoId()).isNotNull();
       
    }

    @Test
    @DisplayName("SendFileWithWrongTypeToTranscript returns exception")
    public void SendFileWithWrongTypeToTranscript() {

        MediaType wrongMediaType = MediaType.APPLICATION_JSON;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(wrongMediaType);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<ExceptionTestDTO> response = testRestTemplate.postForEntity("/", requestEntity, ExceptionTestDTO.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(response.getBody().getDetails()).isEqualTo("Current request is not a multipart request");

    }

    @Test
    @DisplayName("SendFileWithFakeExtensionToTranscript returns exception")
    public void SendFileWithFakeExtensionToTranscript() {

        MediaType mediaType = MediaType.MULTIPART_FORM_DATA;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", FileCreator.createFileImageWithVideoExtensionToBeTranscripted());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<ExceptionTestDTO> response = testRestTemplate.postForEntity("/", requestEntity, ExceptionTestDTO.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(response.getBody().getDetails()).isEqualTo(ExceptionsMessage.AUDIO_EXTRACTION_FAIL);

    }


    @Test
    @DisplayName("SendEmptyFileToTranscript returns exception")
    public void SendEmptyFileToTranscript() {

        MediaType mediaType = MediaType.MULTIPART_FORM_DATA;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", "");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<ExceptionTestDTO> response = testRestTemplate.postForEntity("/", requestEntity, ExceptionTestDTO.class);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

}
