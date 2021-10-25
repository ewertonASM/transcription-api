package br.com.inatel.transcriptGatewayApi.repository;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.inatel.transcriptGatewayApi.model.SnippetSubtitle;
import br.com.inatel.transcriptGatewayApi.util.SnippetSubtitleCreator;

@DataJpaTest
@DisplayName("Tests for snippetRepository Repository")
class SnippetSubtitleRepositoryTest {

    @Autowired
    private SnippetSubtitleRepository snippetSubtitleRepository;

    @Test   
    @DisplayName("Save persists snippetSubtitle when Successful")
    void save_PersistSnippetSubtitle_WhenSuccessful(){
        SnippetSubtitle snippetSubtitleToBeSaved = SnippetSubtitleCreator.createSnippetSubtitleToBeSaved();

        SnippetSubtitle snippetSubtitleSaved = this.snippetSubtitleRepository.save(snippetSubtitleToBeSaved);

        Assertions.assertThat(snippetSubtitleSaved).isNotNull();

        Assertions.assertThat(snippetSubtitleSaved.getId()).isNotNull();

        Assertions.assertThat(snippetSubtitleSaved.getId()).isEqualTo(snippetSubtitleToBeSaved.getId());
    }


    @Test
    @DisplayName("Find By Subtitle returns empty list when no snippetSubtitle is found")
    void findBySnippetSubtitleId_ReturnsEmptyList_WhenSnippetSubtitleQuoteIsNotFound(){
        Optional<SnippetSubtitle> snippetSubtitle = this.snippetSubtitleRepository.findById("xaxa");

        Assertions.assertThat(snippetSubtitle.get()).isNull();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when snippetSubtitle id is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty(){
        SnippetSubtitle snippetSubtitle = new SnippetSubtitle();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.snippetSubtitleRepository.save(snippetSubtitle));
    }

}
