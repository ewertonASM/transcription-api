package br.com.inatel.transcriptGatewayApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.inatel.transcriptGatewayApi.model.SnippetSubtitle;

public interface SnippetSubtitleRepository extends JpaRepository<SnippetSubtitle, String>{

    List<SnippetSubtitle> findByVideoId(String videoId);

}