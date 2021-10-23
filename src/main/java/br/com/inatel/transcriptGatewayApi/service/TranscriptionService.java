package br.com.inatel.transcriptGatewayApi.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.inatel.transcriptGatewayApi.dto.AudioForTranscriptionDTO;
import br.com.inatel.transcriptGatewayApi.envs.Envs;
import lombok.extern.log4j.Log4j2;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

@Log4j2
@Service
public class TranscriptionService {

    public AudioForTranscriptionDTO processToTranscription(String filename){
        
        UUID videoId = UUID.randomUUID();

        try {      

            File source = new File(Envs.TEMP_DIR.concat(filename));		                 
            File target = new File(Envs.TEMP_DIR + videoId + "." + Envs.AUDIO_EXT);                         
                                                                        
            //Audio Attributes                                       
            AudioAttributes audio = new AudioAttributes();                                       
            audio.setBitRate(Envs.BIT_RATE);                                   
            audio.setChannels(Envs.CHANNELS);                                       
            audio.setSamplingRate(Envs.SAMPLING_RATE);                               
                                                                        
            //Encoding attributes                                       
            EncodingAttributes attrs = new EncodingAttributes();        
            attrs.setOutputFormat(Envs.AUDIO_EXT);                                     
            attrs.setAudioAttributes(audio);                            
                                                                        
            //Encode                                                    
            Encoder encoder = new Encoder();                        
            encoder.encode(new MultimediaObject(source), target, attrs);

            log.info("Audio sent for processing!");
                                                                    
        } catch (Exception ex) {                                      
            ex.printStackTrace();                                                                             
        }


        Path path = Paths.get(Envs.TEMP_DIR+videoId+"."+Envs.AUDIO_EXT);
        try {

            byte[] data = Files.readAllBytes(path);

            AudioForTranscriptionDTO message = AudioForTranscriptionDTO.builder().audio(data).videoId(videoId).build();

            return message;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }
    
}
