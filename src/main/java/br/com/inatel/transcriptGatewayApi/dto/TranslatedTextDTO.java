package br.com.inatel.transcriptGatewayApi.dto;

import lombok.Data;
import lombok.Builder;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TranslatedTextDTO {

    @NotEmpty(message = "The translated text cannot be empty")
    private String translatedText;
    
}
