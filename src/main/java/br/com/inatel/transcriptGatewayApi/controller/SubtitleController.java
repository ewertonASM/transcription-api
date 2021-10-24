package br.com.inatel.transcriptGatewayApi.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springdoc.api.annotations.ParameterObject;
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

    @GetMapping("/videos/{id}/subtitle")
	public void findSubtitle(@ParameterObject HttpServletRequest request, HttpServletResponse response,
                @PathVariable String id, @RequestParam(required = false) Optional<String> lang) throws IOException {

        log.info("Downloading subtitle...");
        
        String language = lang.isPresent() ? lang.get() : "";
        
        String subtitleFileName = subtitleService.findSubtitle(id, language);
        
        log.debug(subtitleFileName);

        subtitleService.downloadResource(request, response, subtitleFileName);

        storageService.deleteOne(subtitleFileName);

	}
    
}
