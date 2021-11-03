package br.com.inatel.transcriptGatewayApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.inatel.transcriptGatewayApi.model.SnippetSubtitle;

public interface SnippetSubtitleRepository extends JpaRepository<SnippetSubtitle, String>{

    @Transactional(readOnly = true, timeout = 10)
    List<SnippetSubtitle> findByVideoId(String videoId);

}