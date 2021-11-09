package br.com.inatel.transcriptGatewayApi.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import br.com.inatel.transcriptGatewayApi.dto.AudioForTranscriptionDTO;

public class AudioForTranscriptionCreator {

    // byte[] data = Files.readAllBytes(path);
    
    public static AudioForTranscriptionDTO createAudioToTranscript() {
        
        try {
            
            Path path = Paths.get("dir.wav");
            byte[] data = Files.readAllBytes(path);

            AudioForTranscriptionDTO message = AudioForTranscriptionDTO.builder()
                                                .audio(data)
                                                .videoId(UUID.randomUUID())
                                                .build();

            return message;

        } catch (IOException e) {
            return null;
        }

    }
    // public static SnippetSubtitle createSnippetSubtitle(){

    //     return SnippetSubtitle.builder()
    //             .id("2c9830817c0051f8017c00570a260005")
    //             .videoId("d67dc5f4-ed4d-4f81-9314-38152cb022b1")
    //             .text("o rato roeu a roupa do rei de roma")
    //             .timeLimits("00:00:05,788 --> 00:00:05,788")
    //             .snippet("1/1")
    //             .build();

    // }
    
}
