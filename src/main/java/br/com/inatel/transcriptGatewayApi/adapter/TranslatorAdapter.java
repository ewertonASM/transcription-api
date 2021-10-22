package br.com.inatel.transcriptGatewayApi.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.inatel.transcriptGatewayApi.dto.TranslateRequestDTO;
import br.com.inatel.transcriptGatewayApi.dto.TranslateResponseDTO;
import br.com.inatel.transcriptGatewayApi.envs.Envs;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class TranslatorAdapter {

    @Value("${google_translate.base_url:https://translation.googleapis.com}")
    private String baseUrl;

    @Value("${google_translate.api_key:AIzaSyCYX9soUSBUk55LBfqXpIycVWPEczgjTNk}")
    private String apiKey;

    // @Cacheable(cacheNames = "Company", key="#identifier")
    public TranslateResponseDTO translateText(TranslateRequestDTO translateDTO){
        
        String translatorUrl = baseUrl + "/language/translate/v2?key=" + apiKey;

        return new RestTemplate().postForObject(translatorUrl, translateDTO, TranslateResponseDTO.class);
        
    }

}
