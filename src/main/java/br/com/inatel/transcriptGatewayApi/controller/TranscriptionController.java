package br.com.inatel.transcriptGatewayApi.controller;

import java.util.UUID;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.inatel.transcriptGatewayApi.dto.AudioForTranscriptionDTO;
import br.com.inatel.transcriptGatewayApi.dto.VideoIdDTO;
import br.com.inatel.transcriptGatewayApi.envs.Envs;
import br.com.inatel.transcriptGatewayApi.exception.StorageFileNotFoundException;
import br.com.inatel.transcriptGatewayApi.service.SendMessageService;
import br.com.inatel.transcriptGatewayApi.service.StorageService;
import br.com.inatel.transcriptGatewayApi.service.TranscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
public class TranscriptionController {

    private final SendMessageService sendMessage;

	private final StorageService storageService;

	private final TranscriptionService transcriptionService;

	@PostMapping("/")
	public ResponseEntity<VideoIdDTO> handleFileUpload(@ParameterObject @RequestParam("file") MultipartFile file) {

		log.debug("Storing file...", file);
		storageService.store(file);		
		log.debug("Stored file.", file);
		
		AudioForTranscriptionDTO message = transcriptionService.processToTranscription(file.getOriginalFilename());
		sendMessage.messageSender(message);

		
		log.debug("Deleting temp files...", file.getOriginalFilename(), message.videoId+"."+Envs.AUDIO_EXT);
		storageService.deleteOne(file.getOriginalFilename());
		storageService.deleteOne(message.videoId+"."+Envs.AUDIO_EXT);
		log.debug("Files deleted!");
	
		return new ResponseEntity<>(VideoIdDTO.builder().videoId(message.getVideoId()).build(), HttpStatus.CREATED);
	}
	
	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}


}
