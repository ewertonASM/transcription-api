package br.com.inatel.transcriptGatewayApi.service;


import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

// import academy.devdojo.springboot2.domain.Anime;
// import academy.devdojo.springboot2.exception.BadRequestException;
// import academy.devdojo.springboot2.repository.AnimeRepository;
// import academy.devdojo.springboot2.service.AnimeService;
// import academy.devdojo.springboot2.util.AnimeCreator;
// import academy.devdojo.springboot2.util.AnimePostDTOCreator;
// import academy.devdojo.springboot2.util.AnimePutDTOCreator;

@ExtendWith(SpringExtension.class)
// @RequiredArgsConstructor
public class TranscriptionServiceTest {

    // @InjectMocks
    private TranscriptionService transcriptionServiceTest;

    @Autowired
    private StorageService storageServiceTest;
    
//     @Mock
//     private AnimeRepository animeRepositoryMock;

    @BeforeEach
    void setUp(){

        // BDDMockito.when(animeRepositoryMock.findAll())
        //         .thenReturn(List.of(AnimeCreator.createValidAnime()));

        // BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
        //         .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        // BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
        //         .thenReturn(List.of(AnimeCreator.createValidAnime()));

        // BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
        //         .thenReturn(AnimeCreator.createValidAnime());

        // BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }
    
    @Test
    @DisplayName("listAll returns list of anime inside page object when successful")
    void listAll_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() throws IOException {
        // String expectedName = AnimeCreator.createValidAnime().getName();

        Path path = Paths.get("/home/emoura/Workspace/Inatel/Video-translator/Video-api/transcription-api/src/test/java/br/com/inatel/transcriptGatewayApi/test-data/1.mp4");
        String name = "1.mp4";
        String originalFileName = "1.mp4";
        String contentType = "video/mp4";
        byte[] content = null;
        
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            
        }

        MultipartFile result = new MockMultipartFile(name,
                        originalFileName, contentType, content);

        storageServiceTest.store(result);
        // String file = new String("/home/emoura/Workspace/Inatel/Video-translator/Video-api/transcription-api/src/test/java/br/com/inatel/transcriptGatewayApi/test-data/1.mp4");
        
        
        // transcriptionServiceTest.processToTranscription("/home/emoura/Workspace/Inatel/Video-translator/Video-api/transcription-api/tmp/1.mp4");

        // Assertions.assertThat(animePage).isNotNull();

        // Assertions.assertThat(animePage.toList())
        //         .isNotEmpty()
        //         .hasSize(1);

        // Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
    }

//     @Test
//     @DisplayName("listAllNonPageable returns list of anime when successful")
//     void listAllNonPageable_ReturnsListOfAnimes_WhenSuccessful(){
//         String expectedName = AnimeCreator.createValidAnime().getName();

//         List<Anime> animes = animeService.listAllNonPageable();

//         Assertions.assertThat(animes)
//                 .isNotNull()
//                 .isNotEmpty()
//                 .hasSize(1);

//         Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
//     }

//     @Test
//     @DisplayName("findByIdOrThrowBadRequestException returns anime when successful")
//     void findByIdOrThrowBadRequestException_ReturnsAnime_WhenSuccessful(){
//         Long expectedId = AnimeCreator.createValidAnime().getId();

//         Anime anime = animeService.findByIdOrThrowBadRequestException(1L);

//         Assertions.assertThat(anime).isNotNull();

//         Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);
//     }

//     @Test
//     @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when anime is not found")
//     void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenAnimeIsNotFound(){
//         BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
//                 .thenReturn(Optional.empty());

//         Assertions.assertThatExceptionOfType(BadRequestException.class)
//                 .isThrownBy(() -> animeService.findByIdOrThrowBadRequestException(1L));
//     }

//     @Test
//     @DisplayName("findByName returns a list of anime when successful")
//     void findByName_ReturnsListOfAnime_WhenSuccessful(){
//         String expectedName = AnimeCreator.createValidAnime().getName();

//         List<Anime> animes = animeService.findByName("anime");

//         Assertions.assertThat(animes)
//                 .isNotNull()
//                 .isNotEmpty()
//                 .hasSize(1);

//         Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);
//     }

//     @Test
//     @DisplayName("findByName returns an empty list of anime when anime is not found")
//     void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound(){
//         BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
//                 .thenReturn(Collections.emptyList());

//         List<Anime> animes = animeService.findByName("anime");

//         Assertions.assertThat(animes)
//                 .isNotNull()
//                 .isEmpty();

//     }

//     @Test
//     @DisplayName("save returns anime when successful")
//     void save_ReturnsAnime_WhenSuccessful(){

//         Anime anime = animeService.save(AnimePostDTOCreator.createAnimePostDTO());

//         Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());

//     }

//     @Test
//     @DisplayName("replace updates anime when successful")
//     void replace_UpdatesAnime_WhenSuccessful(){

//         Assertions.assertThatCode(() ->animeService.replace(AnimePutDTOCreator.createAnimePutDTO()))
//                 .doesNotThrowAnyException();

//     }

//     @Test
//     @DisplayName("delete removes anime when successful")
//     void delete_RemovesAnime_WhenSuccessful(){

//         Assertions.assertThatCode(() ->animeService.delete(1L))
//                 .doesNotThrowAnyException();

//     }
}