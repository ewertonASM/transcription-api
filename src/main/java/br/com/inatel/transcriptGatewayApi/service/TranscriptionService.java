package br.com.inatel.transcriptGatewayApi.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.springframework.stereotype.Service;

import br.com.inatel.transcriptGatewayApi.dto.AudioForTranscriptionDTO;
import lombok.extern.log4j.Log4j2;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;


@Log4j2
@Service
public class TranscriptionService {


    public AudioForTranscriptionDTO processToTranscription(String filename){

        try {      

            File source = new File("/home/emoura/Workspace/Inatel/Video-translator/Video-api/idp-stonks-test/upload-dir/".concat(filename));		                 
            File target = new File("/home/emoura/Workspace/Inatel/Video-translator/Video-api/idp-stonks-test/upload-dir/micro.wav");                         
                                                                        
            //Audio Attributes                                       
            AudioAttributes audio = new AudioAttributes();                                       
            audio.setBitRate(128000);                                   
            audio.setChannels(1);                                       
            audio.setSamplingRate(16000);                               
                                                                        
            //Encoding attributes                                       
            EncodingAttributes attrs = new EncodingAttributes();        
            attrs.setOutputFormat("wav");                                     
            attrs.setAudioAttributes(audio);                            
                                                                        
            //Encode                                                    
            Encoder encoder = new Encoder();                        
            encoder.encode(new MultimediaObject(source), target, attrs);
                                                                    
        } catch (Exception ex) {                                      
            ex.printStackTrace();                                                                             
        }


        Path path = Paths.get("/home/emoura/Workspace/Inatel/Video-translator/Video-api/idp-stonks-test/upload-dir/micro.wav");
        try {

            byte[] data = Files.readAllBytes(path);

            AudioForTranscriptionDTO message = AudioForTranscriptionDTO.builder().audio(data).videoId(UUID.randomUUID()).build();

            return message;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
    
}
