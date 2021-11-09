package br.com.inatel.transcriptGatewayApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MultipartException extends RuntimeException {

    public MultipartException(String message) {
        super(message);
    }    
    
}