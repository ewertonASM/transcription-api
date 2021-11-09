package br.com.inatel.transcriptGatewayApi.util;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionTestDTO {

    protected String title;
    protected int status;
    protected String details;
    // protected String developerMessage;
    protected String timestamp;
    
}