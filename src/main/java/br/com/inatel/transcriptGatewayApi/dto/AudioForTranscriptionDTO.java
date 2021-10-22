package br.com.inatel.transcriptGatewayApi.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AudioForTranscriptionDTO implements Serializable {


    @NotEmpty(message = "The videoId cannot be empty")
    public UUID videoId;

    @NotEmpty(message = "The audio cannot be empty")
    public byte[] audio;
                           
}
