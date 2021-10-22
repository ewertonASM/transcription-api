package br.com.inatel.transcriptGatewayApi.controller;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.inatel.transcriptGatewayApi.service.SubtitleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
public class SubtitleController {

	private final SubtitleService subtitleService;


    @GetMapping("/videos/{id}/subtitle")
	public void findSubtitle(HttpServletRequest request, HttpServletResponse response,
                @PathVariable String id, @RequestParam(required = false) Optional<String> lang) throws InterruptedException, ExecutionException {

        log.info("Downloading subtitle...");

        String language = lang.isPresent() ? lang.get() : "";
        
        String subtitleFileName = subtitleService.findSubtitle(id, language);
        
        log.debug("Downloading subtitle..." + subtitleFileName);
        try {
            subtitleService.downloadPDFResource(request, response, subtitleFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }


	}
    
}
