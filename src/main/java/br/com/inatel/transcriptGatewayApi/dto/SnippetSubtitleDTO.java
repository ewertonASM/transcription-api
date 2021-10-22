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
public class SnippetSubtitleDTO {

    @NotEmpty(message = "The videoId cannot be empty")
    String videoId;

    @NotEmpty(message = "The text cannot be empty")
    String text;

    @NotEmpty(message = "The time limits cannot be empty")
    String timeLimits;

    @NotEmpty(message = "The snippet cannot be empty")
    String snippet;
    
}
