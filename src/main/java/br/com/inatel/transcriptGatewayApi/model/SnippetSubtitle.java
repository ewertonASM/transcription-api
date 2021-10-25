package br.com.inatel.transcriptGatewayApi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class SnippetSubtitle implements Comparable<SnippetSubtitle>{
    
    @Id
    @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
    private String id;

    private String videoId;

    @Lob 
    @Column(length=512)
    private String text;

    private String timeLimits;

    private String snippet;

    @Override
    public int compareTo(SnippetSubtitle o) {

        return Integer.parseInt(this.snippet.split("/")[0]) - Integer.parseInt(o.getSnippet().split("/")[0]); 

    }
    
}
