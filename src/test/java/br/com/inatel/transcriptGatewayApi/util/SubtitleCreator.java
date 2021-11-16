package br.com.inatel.transcriptGatewayApi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.inatel.transcriptGatewayApi.exception.BadRequestException;

public class SubtitleCreator {

    public static ResponseEntity<InputStreamResource> createValidSubtitle() {
        File file = new File("/home/emoura/Workspace/Inatel/Video-translator/Video-api/transcription-api/src/test/java/br/com/inatel/transcriptGatewayApi/test-data/9ab7d462-ba0b-4da6-9726-b88a9a45d3cf.srt");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

        InputStreamResource resource;
            try {
                resource = new InputStreamResource(new FileInputStream(file));
                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(file.length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } catch (FileNotFoundException e) {
                throw new BadRequestException(e.getMessage());
            }
    

    }
    
}






        // if (file.exists()) {

        // String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        // if (mimeType == null) {

            
        