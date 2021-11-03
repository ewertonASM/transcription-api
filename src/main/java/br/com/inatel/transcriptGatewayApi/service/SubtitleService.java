package br.com.inatel.transcriptGatewayApi.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import br.com.inatel.transcriptGatewayApi.adapter.TranslatorAdapter;
import br.com.inatel.transcriptGatewayApi.dto.TranslateRequestDTO;
import br.com.inatel.transcriptGatewayApi.dto.TranslateResponseDTO;
import br.com.inatel.transcriptGatewayApi.envs.Envs;
import br.com.inatel.transcriptGatewayApi.exception.BadRequestException;
import br.com.inatel.transcriptGatewayApi.model.SnippetSubtitle;
import br.com.inatel.transcriptGatewayApi.repository.SnippetSubtitleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@EnableAsync
@RequiredArgsConstructor
public class SubtitleService {

    private final TranslatorAdapter translatorAdapter; 

    private final SnippetSubtitleRepository snippetSubtitleRepository;

    public String findSubtitle(String videoId, String language) throws IOException {

        String subtitleFileName = Envs.TEMP_DIR + videoId + "." + Envs.SUBTITLE_EXT;

        List<SnippetSubtitle> translatedSubtitle = new LinkedList<>();
        List<SnippetSubtitle> snippets = snippetSubtitleRepository.findByVideoId(videoId);

        if(snippets.isEmpty()){
            throw new BadRequestException("subtitle not found!");
        }
        if(snippets.size()+1 != Integer.parseInt(snippets.get(0).getSnippet().split("/")[1])){
            throw new BadRequestException("subtitle in processing, try again later");
        }

        if(!language.isEmpty()){

            log.info("Translating subtittle...");
            translatedSubtitle = translate(snippets, language);
            log.info("translation completed!");

        }

        FileWriter writer = new FileWriter(subtitleFileName);

        List<SnippetSubtitle> snippetSubtitleForFile = language.isEmpty() ? snippets : translatedSubtitle;

    
        Collections.sort(snippetSubtitleForFile);
        
        for(SnippetSubtitle snippet:snippetSubtitleForFile){

            writer.write(snippet.getSnippet().split("/")[0] + System.lineSeparator());
            writer.write(snippet.getTimeLimits() + System.lineSeparator());
            writer.write(snippet.getText() + System.lineSeparator());
            writer.write(System.lineSeparator());

        }

        writer.close();

        return subtitleFileName;

    }

    public void downloadResource(HttpServletRequest request, HttpServletResponse response,
			        String subtitleFileName) throws IOException {

		File file = new File(subtitleFileName);
		if (file.exists()) {

			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);

			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			FileCopyUtils.copy(inputStream, response.getOutputStream());

		}
	}

    // @Async
    private List<SnippetSubtitle> translate(List<SnippetSubtitle> snippets, String language){

        List<SnippetSubtitle> translatedSubtitle = new LinkedList<>();

        snippets.parallelStream().forEach(snippet -> {

            SnippetSubtitle translatedSnippet = snippet;

            String textToTranslate = snippet.getText();
            TranslateRequestDTO translateRequestDTO = TranslateRequestDTO.builder()
                                                            .q(textToTranslate)
                                                            .source(Envs.TRANSLATE_SOURCE)
                                                            .target(language)
                                                            .format(Envs.TRANSLATE_FORMAT)
                                                            .build();

        
            TranslateResponseDTO translateResponseDTO = translatorAdapter.translateText(translateRequestDTO);

            log.info("Translating subtitle " + translatedSnippet.getSnippet());
            translatedSnippet.setText(translateResponseDTO.getData().getTranslations().get(0).getTranslatedText());


            translatedSubtitle.add(translatedSnippet);

        });

        return translatedSubtitle;



    }

}
