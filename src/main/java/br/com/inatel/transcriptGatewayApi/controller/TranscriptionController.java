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
import br.com.inatel.transcriptGatewayApi.envs.Envs;
import br.com.inatel.transcriptGatewayApi.exception.BadRequestException;
import br.com.inatel.transcriptGatewayApi.exception.StorageFileNotFoundException;
import br.com.inatel.transcriptGatewayApi.handler.ExceptionsMessage;
import br.com.inatel.transcriptGatewayApi.service.SendMessageService;
import br.com.inatel.transcriptGatewayApi.service.StorageService;
import br.com.inatel.transcriptGatewayApi.service.TranscriptionService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TranscriptionController {

    private final SendMessageService sendMessage;

	private final StorageService storageService;

	private final TranscriptionService transcriptionService;

	@PostMapping("/")
	public ResponseEntity<UUID> handleFileUpload(@ParameterObject @RequestParam("file") MultipartFile file) {

		try {

			storageService.store(file);
			AudioForTranscriptionDTO message = transcriptionService.processToTranscription(file.getOriginalFilename());
			sendMessage.messageSender(message);
			
			storageService.deleteOne(file.getOriginalFilename());
			storageService.deleteOne(message.videoId+"."+Envs.AUDIO_EXT);
	
			return new ResponseEntity<>(message.getVideoId(), HttpStatus.CREATED);
		
		} catch (Exception e) {
		
			throw new BadRequestException(ExceptionsMessage.FILE_PROCESSING_FAILED);
		
		}
       
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}


}
