package br.com.inatel.transcriptGatewayApi.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class MultipartFileCreate {

    public static MockMultipartFile createValidFileToBeTranscripted(){

        Path path = Paths.get("/home/emoura/Workspace/Inatel/Video-translator/Video-api/transcription-api/src/test/java/br/com/inatel/transcriptGatewayApi/test-data/1.mp4");
        String name = "file";
        String originalFileName = "1.mp4";
        String contentType = "video/mp4";
        byte[] content = null;
        try {
        content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }

        return new MockMultipartFile(name, originalFileName, contentType, content);

    }


    public static MockMultipartFile createInvalidFileToBeTranscripted(){

        Path path = Paths.get("/home/emoura/Workspace/Inatel/Video-translator/Video-api/transcription-api/src/test/java/br/com/inatel/transcriptGatewayApi/test-data/imagem.jpeg");
        String name = "file";
        String originalFileName = "imagem.jpeg";
        String contentType = "image/jpeg";
        byte[] content = null;
        try {
        content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }

        return new MockMultipartFile(name, originalFileName, contentType, content);


    }

    public static MockMultipartFile createOtherFileWithVideoExtensionToBeTranscripted(){

        Path path = Paths.get("/home/emoura/Workspace/Inatel/Video-translator/Video-api/transcription-api/src/test/java/br/com/inatel/transcriptGatewayApi/test-data/imagem.mp4");
        String name = "file";
        String originalFileName = "imagem.jpeg";
        String contentType = "image/jpeg";
        byte[] content = null;
        try {
        content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }

        return new MockMultipartFile(name, originalFileName, contentType, content);

        

    }
    
}
