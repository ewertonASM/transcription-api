package br.com.inatel.transcriptGatewayApi.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TranslateResponseDTO {

    @NotEmpty(message = "The data cannot be empty")
    private TranslationsDTO data;
    
}
