package br.com.inatel.transcriptGatewayApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;

@Service
public class StreamingService {

    private static final String FORMAT="classpath:upload-dir/%s";

    @Autowired
    private ResourceLoader resourceLoader;


    public Mono<Resource> getVideo(String title){
     return Mono.fromSupplier(()->resourceLoader.
             getResource(String.format(FORMAT,title)))   ;
    }

}
