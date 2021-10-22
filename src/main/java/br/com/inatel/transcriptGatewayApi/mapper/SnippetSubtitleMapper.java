package br.com.inatel.transcriptGatewayApi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.inatel.transcriptGatewayApi.dto.SnippetSubtitleDTO;
import br.com.inatel.transcriptGatewayApi.model.SnippetSubtitle;

@Mapper(componentModel = "spring")
public abstract class SnippetSubtitleMapper {
    
    public static final SnippetSubtitleMapper INSTANCE = Mappers.getMapper(SnippetSubtitleMapper.class);
    public abstract SnippetSubtitle toSnippetSubtitle(SnippetSubtitleDTO snippetSubtitleDTO);

}
