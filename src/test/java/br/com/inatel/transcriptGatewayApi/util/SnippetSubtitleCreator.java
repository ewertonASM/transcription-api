package br.com.inatel.transcriptGatewayApi.util;

import br.com.inatel.transcriptGatewayApi.model.SnippetSubtitle;

public class SnippetSubtitleCreator {

    public static SnippetSubtitle createSnippetSubtitleToBeSaved(){
        return SnippetSubtitle.builder()
                .videoId("d67dc5f4-ed4d-4f81-9314-38152cb022b1")
                .text("o rato roeu a roupa do rei de roma")
                .timeLimits("00:00:05,788 --> 00:00:05,788")
                .snippet("1/1")
                .build();
    }
    public static SnippetSubtitle createSnippetSubtitle(){

        return SnippetSubtitle.builder()
                .id("2c9830817c0051f8017c00570a260005")
                .videoId("d67dc5f4-ed4d-4f81-9314-38152cb022b1")
                .text("o rato roeu a roupa do rei de roma")
                .timeLimits("00:00:05,788 --> 00:00:05,788")
                .snippet("1/1")
                .build();

    }
}
