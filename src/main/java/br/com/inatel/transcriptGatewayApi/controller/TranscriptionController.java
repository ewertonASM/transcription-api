package br.com.inatel.transcriptGatewayApi.controller;

import java.io.IOException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.inatel.transcriptGatewayApi.dto.AudioForTranscriptionDTO;
import br.com.inatel.transcriptGatewayApi.service.SendMessageService;
import br.com.inatel.transcriptGatewayApi.service.StreamingService;
import br.com.inatel.transcriptGatewayApi.service.TranscriptionService;
import br.com.inatel.transcriptGatewayApi.storage.StorageFileNotFoundException;
import br.com.inatel.transcriptGatewayApi.storage.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@Controller
@RequiredArgsConstructor
public class TranscriptionController {

    private final SendMessageService sendMessage;

    private final StreamingService service;

	private final StorageService storageService;

	private final TranscriptionService transcriptionService;

	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		model.addAttribute("files", storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(TranscriptionController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList()));

		return "uploadForm";
	}

    @GetMapping(value = "/upload-dir/{filename:.+}", produces = "video/mp4")
	@ResponseBody
	public Mono<Resource> serveFile(@PathVariable String filename) {

        return service.getVideo(filename);
	}

	@PostMapping("/")
	public ResponseEntity<UUID> handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

        storageService.store(file);

        AudioForTranscriptionDTO message = transcriptionService.processToTranscription(file.getOriginalFilename());
       
        sendMessage.messageSender("audio-extract", message);

        return new ResponseEntity<>(message.getVideoId(), HttpStatus.CREATED);
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}


}
