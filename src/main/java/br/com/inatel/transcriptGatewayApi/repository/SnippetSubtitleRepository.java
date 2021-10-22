package br.com.inatel.transcriptGatewayApi.repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.transcriptGatewayApi.model.SnippetSubtitle;
import io.netty.util.concurrent.Future;

public interface SnippetSubtitleRepository extends JpaRepository<SnippetSubtitle, String>{

    List<SnippetSubtitle> findByVideoId(String videoId);
    // List<Future<SnippetSubtitle>> findByVideoId(String videoId);

}