package br.com.inatel.transcriptGatewayApi.util;

// import java.io.File;

import org.springframework.core.io.FileSystemResource;

public class FileCreator {

    public static FileSystemResource createValidFileToBeTranscripted(){
        return new FileSystemResource("/home/emoura/Workspace/Inatel/Video-translator/Video-api/transcription-api/src/test/java/br/com/inatel/transcriptGatewayApi/test-data/micro.mp4");
    }

    public static FileSystemResource createInvalidFileToBeTranscripted(){
        return new FileSystemResource("/home/emoura/Workspace/Inatel/Video-translator/Video-api/transcription-api/src/test/java/br/com/inatel/transcriptGatewayApi/test-data/imagem.jpeg");
    }

    public static FileSystemResource createFileImageWithVideoExtensionToBeTranscripted(){
        return new FileSystemResource("/home/emoura/Workspace/Inatel/Video-translator/Video-api/transcription-api/src/test/java/br/com/inatel/transcriptGatewayApi/test-data/imagem.mp4");
    }
    

}
