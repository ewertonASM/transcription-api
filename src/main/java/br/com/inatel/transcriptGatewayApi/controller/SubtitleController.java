package br.com.inatel.transcriptGatewayApi.controller;

import java.io.FileNotFoundException;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.inatel.transcriptGatewayApi.service.StorageService;
import br.com.inatel.transcriptGatewayApi.service.SubtitleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
public class SubtitleController {

	private final SubtitleService subtitleService;

	private final StorageService storageService;

    // @GetMapping("/videos/{id}/subtitle")
	// public void findSubtitle(@ParameterObject HttpServletRequest request, HttpServletResponse response,
    //             @PathVariable UUID id, @RequestParam(required = false) Optional<String> lang) {

    //     log.info("Downloading subtitle...");

    //     String language = lang.isPresent() ? lang.get() : "";
        
    //     String subtitleFileName = subtitleService.findSubtitle(id.toString(), language);
        
    //     log.debug(subtitleFileName);

    //     subtitleService.downloadResource(request, response, subtitleFileName);

    //     storageService.deleteOne(subtitleFileName);

	// }




    @GetMapping("/videos/{id}/subtitle")
	public ResponseEntity<InputStreamResource> findSubtitle(@PathVariable UUID id, @RequestParam(required = false) Optional<String> lang){

        log.info("Downloading subtitle...");

        String language = lang.isPresent() ? lang.get() : "";

        log.info(id);
        
        String subtitleFileName = subtitleService.findSubtitle(id.toString(), language);
        
        log.debug(subtitleFileName);

        ResponseEntity<InputStreamResource> response = subtitleService.downloadResource(subtitleFileName);
        
        storageService.deleteOne(subtitleFileName);

        return response;

	}

    
}
