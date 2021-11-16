package br.com.inatel.transcriptGatewayApi.unit.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.inatel.transcriptGatewayApi.dto.AudioForTranscriptionDTO;
import br.com.inatel.transcriptGatewayApi.handler.ExceptionsMessage;
import br.com.inatel.transcriptGatewayApi.service.SendMessageService;
import br.com.inatel.transcriptGatewayApi.service.StorageService;
import br.com.inatel.transcriptGatewayApi.service.TranscriptionService;
import br.com.inatel.transcriptGatewayApi.util.MultipartFileCreate;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class TranscriptionControllerTest {
    
    private static final UUID VIDEO_ID = UUID.randomUUID();

    @Autowired
    MockMvc mockMvc;

    @Mock
    private TranscriptionService transcriptionServiceMock;

    @Mock
    private StorageService storageServiceMock;

    @Mock
    private SendMessageService sendMessageService;

    private AudioForTranscriptionDTO message;


    @BeforeEach
    void setUp(){

        byte[] content = {};
        message = new AudioForTranscriptionDTO(VIDEO_ID, content);

    }
    
    @Test
    @DisplayName("SendValidFileToTranscript returns video id when successful")
    public void SendValidFileToTranscript() throws Exception {

        Mockito.when(transcriptionServiceMock.processToTranscription(ArgumentMatchers.anyString())).thenReturn(message);

        MockMultipartFile videFile = MultipartFileCreate.createValidFileToBeTranscripted();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/").file(videFile))
                    .andExpect(status().is(201))
                    .andExpect(jsonPath("$.videoId").isNotEmpty());
       
    }

    @Test
    @DisplayName("SendFileWithWrongTypeToTranscript returns exception")
    public void SendFileWithWrongTypeToTranscript() throws Exception {

        Mockito.when(transcriptionServiceMock.processToTranscription(ArgumentMatchers.anyString())).thenReturn(message);

        MockMultipartFile videFile = MultipartFileCreate.createInvalidFileToBeTranscripted();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/").file(videFile))
                    .andExpect(status().is(400))
                    .andExpect(jsonPath("$.details").value(ExceptionsMessage.AUDIO_EXTRACTION_FAIL));
    }

    @Test
    @DisplayName("SendFileWithFakeExtensionToTranscript returns exception")
    public void SendFileWithFakeExtensionToTranscript() throws Exception {

        Mockito.when(transcriptionServiceMock.processToTranscription(ArgumentMatchers.anyString())).thenReturn(message);

        MockMultipartFile videFile = MultipartFileCreate.createInvalidFileToBeTranscripted();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/").file(videFile))
                    .andExpect(status().is(400))
                    .andExpect(jsonPath("$.details").value(ExceptionsMessage.AUDIO_EXTRACTION_FAIL));

    }


    @Test
    @DisplayName("SendEmptyFileToTranscript returns exception")
    public void SendEmptyFileToTranscript() throws Exception {

        Mockito.when(transcriptionServiceMock.processToTranscription(ArgumentMatchers.anyString())).thenReturn(message);

        MockMultipartFile videFile = MultipartFileCreate.createOtherFileWithVideoExtensionToBeTranscripted();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/").file(videFile))
                    .andExpect(status().is(400))
                    .andExpect(jsonPath("$.details").value(ExceptionsMessage.AUDIO_EXTRACTION_FAIL));


    }

}