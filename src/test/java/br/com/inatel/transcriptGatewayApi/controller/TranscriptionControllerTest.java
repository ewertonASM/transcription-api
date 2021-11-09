package br.com.inatel.transcriptGatewayApi.controller;
// package academy.devdojo.springboot2.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import br.com.inatel.transcriptGatewayApi.dto.AudioForTranscriptionDTO;
import br.com.inatel.transcriptGatewayApi.dto.VideoIdDTO;
import br.com.inatel.transcriptGatewayApi.service.SendMessageService;
import br.com.inatel.transcriptGatewayApi.service.StorageService;
import br.com.inatel.transcriptGatewayApi.service.TranscriptionService;
import br.com.inatel.transcriptGatewayApi.util.AudioForTranscriptionCreator;
import br.com.inatel.transcriptGatewayApi.util.FileCreator;
import br.com.inatel.transcriptGatewayApi.util.MultipartFileCreate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
// @ExtendWith(SpringExtension.class)
class TranscriptionControllerTest {
    
    private static final UUID VIDEO_ID = UUID.randomUUID();

    @Autowired
    MockMvc mockMvc;

    // @InjectMocks
    // private TranscriptionController transcriptionController;

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

        // Mockito.when(message.getVideoId()).thenReturn(VIDEO_ID);


        // BDDMockito.when(transcriptionServiceMock.processToTranscription(ArgumentMatchers.anyString()))
        //         .thenReturn(AudioForTranscriptionCreator.createAudioToTranscript());

        // BDDMockito.when(storageServiceMock.store(ArgumentMatchers.anyLong()))
        //         .thenReturn(AnimeCreator.createValidAnime());

        // BDDMockito.when(transcriptionServiceMock.findByName(ArgumentMatchers.anyString()))
        //         .thenReturn(List.of(AnimeCreator.createValidAnime()));

        // BDDMockito.when(transcriptionServiceMock.save(ArgumentMatchers.any(AnimePostDTO.class)))
        //         .thenReturn(AnimeCreator.createValidAnime());

        // BDDMockito.doNothing().when(transcriptionServiceMock).replace(ArgumentMatchers.any(AnimePutDTO.class));

        // BDDMockito.doNothing().when(transcriptionServiceMock).delete(ArgumentMatchers.anyLong());
    }
    // @Test
    // @DisplayName("list returns list of anime inside page object when successful")
    // void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful(){

    //     // UUID expectedName = AudioForTranscriptionCreator.createAudioToTranscript().getVideoId();


    //     Path path = Paths.get("/home/emoura/Workspace/Inatel/Video-translator/Video-api/transcription-api/src/test/java/br/com/inatel/transcriptGatewayApi/test-data/1.mp4");
    //     String name = "1.mp4";
    //     String originalFileName = "1.mp4";
    //     String contentType = "video/mp4";
    //     byte[] content = null;
    //     try {
    //     content = Files.readAllBytes(path);
    //     } catch (final IOException e) {
    //     }
    //     MultipartFile result = new MockMultipartFile(name,
    //                     originalFileName, contentType, content);



    //     // VideoIdDTO videoIdDTO = transcriptionController.handleFileUpload(result).getBody();

    //     // Assertions.assertThat(animePage).isNotNull();

    //     // Assertions.assertThat(animePage.toList())
    //     //         .isNotEmpty()
    //     //         .hasSize(1);

    //     // Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    // }

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
                    .andExpect(status().is(400));
                    // .andExpect(jsonPath("$.videoId").isNotEmpty());
    }

    @Test
    @DisplayName("SendFileWithFakeExtensionToTranscript returns exception")
    public void SendFileWithFakeExtensionToTranscript() throws Exception {

        Mockito.when(transcriptionServiceMock.processToTranscription(ArgumentMatchers.anyString())).thenReturn(message);

        MockMultipartFile videFile = MultipartFileCreate.createInvalidFileToBeTranscripted();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/").file(videFile))
                    .andExpect(status().is(400));
                    // .andExpect(jsonPath("$.videoId").isNotEmpty());
    }


    @Test
    @DisplayName("SendEmptyFileToTranscript returns exception")
    public void SendEmptyFileToTranscript() throws Exception {

        Mockito.when(transcriptionServiceMock.processToTranscription(ArgumentMatchers.anyString())).thenReturn(message);

        MockMultipartFile videFile = MultipartFileCreate.createOtherFileWithVideoExtensionToBeTranscripted();

        mockMvc.perform(MockMvcRequestBuilders.multipart("/").file(videFile))
                    .andExpect(status().is(400));
                    // .andExpect(jsonPath("$.videoId").isNotEmpty());

    }


// //     @Test
// //     @DisplayName("listAll returns list of anime when successful")
// //     void listAll_ReturnsListOfAnimes_WhenSuccessful(){
// //         String expectedName = AnimeCreator.createValidAnime().getName();

// //         List<Anime> animes = transcriptionController.listAll().getBody();

// //         Assertions.assertThat(animes)
// //                 .isNotNull()
// //                 .isNotEmpty()
// //                 .hasSize(1);

// //         Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
// //     }

// //     @Test
// //     @DisplayName("findById returns anime when successful")
// //     void findById_ReturnsAnime_WhenSuccessful(){
// //         Long expectedId = AnimeCreator.createValidAnime().getId();

// //         Anime anime = transcriptionController.findById(1L).getBody();

// //         Assertions.assertThat(anime).isNotNull();

// //         Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
// //     }

// //     @Test
// //     @DisplayName("findByName returns a list of anime when successful")
// //     void findByName_ReturnsListOfAnime_WhenSuccessful(){
// //         String expectedName = AnimeCreator.createValidAnime().getName();

// //         List<Anime> animes = transcriptionController.findByName("anime").getBody();

// //         Assertions.assertThat(animes)
// //                 .isNotNull()
// //                 .isNotEmpty()
// //                 .hasSize(1);

// //         Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
// //     }

// //     @Test
// //     @DisplayName("findByName returns an empty list of anime when anime is not found")
// //     void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound(){
// //         BDDMockito.when(transcriptionServiceMock.findByName(ArgumentMatchers.anyString()))
// //                 .thenReturn(Collections.emptyList());

// //         List<Anime> animes = transcriptionController.findByName("anime").getBody();

// //         Assertions.assertThat(animes)
// //                 .isNotNull()
// //                 .isEmpty();

// //     }

// //     @Test
// //     @DisplayName("save returns anime when successful")
// //     void save_ReturnsAnime_WhenSuccessful(){

// //         Anime anime = transcriptionController.save(AnimePostDTOCreator.createAnimePostDTO()).getBody();

// //         Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());

// //     }

// //     @Test
// //     @DisplayName("replace updates anime when successful")
// //     void replace_UpdatesAnime_WhenSuccessful(){

// //         Assertions.assertThatCode(() ->transcriptionController.replace(AnimePutDTOCreator.createAnimePutDTO()))
// //                 .doesNotThrowAnyException();

// //         ResponseEntity<Void> entity = transcriptionController.replace(AnimePutDTOCreator.createAnimePutDTO());

// //         Assertions.assertThat(entity).isNotNull();

// //         Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
// //     }

// //     @Test
// //     @DisplayName("delete removes anime when successful")
// //     void delete_RemovesAnime_WhenSuccessful(){

// //         Assertions.assertThatCode(() ->transcriptionController.delete(1L))
// //                 .doesNotThrowAnyException();

// //         ResponseEntity<Void> entity = transcriptionController.delete(1L);

// //         Assertions.assertThat(entity).isNotNull();

// //         Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
// //     }
}