package br.com.inatel.transcriptGatewayApi.dto;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VideoIdDTO {

    @NotEmpty(message = "Video ID cannot be empty")
    UUID videoId;

}
