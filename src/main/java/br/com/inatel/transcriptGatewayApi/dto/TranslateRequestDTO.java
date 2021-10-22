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
public class TranslateRequestDTO {

    @NotEmpty(message = "The text cannot be empty")
    private String q;

    @NotEmpty(message = "The source cannot be empty")
    private String source;

    @NotEmpty(message = "The target cannot be empty")
    private String target;

    @NotEmpty(message = "The format cannot be empty")
    private String format;
    
}
