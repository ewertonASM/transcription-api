package br.com.inatel.transcriptGatewayApi.dto;

import lombok.Data;
import lombok.Builder;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TranslationsDTO {

    @NotEmpty(message = "The videoId cannot be empty")
    private List<TranslatedTextDTO> translations;
    
}
