package br.com.inatel.transcriptGatewayApi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
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

    // @Override
    // public int compareTo(SnippetSubtitle a, SnippetSubtitle b) {
    //     // TODO Auto-generated method stub
    //     return Integer.parseInt(a.getSnippet().split("/")[0]) - Integer.parseInt(b.getSnippet().split("/")[0]); 
    // }

    @Override
    public int compareTo(SnippetSubtitle o) {


        if (this.snippet == null && o.getSnippet() == null)
            return 0;
        if (this.snippet == null && o.getSnippet() != null)
            return -1;
        if (this.snippet != null && o.getSnippet() == null)
            return +1;

        
        
        // if (o.getSnippet() == null) {
        //     log.info(">>**********************");
        //     log.info(this.snippet + "==" + o.getSnippet());
        //     log.info(this.snippet.split("/")[0] + "==" + o.getSnippet().split("/")[0]);
        //     log.info(">>**********************");
        //     return (o.getSnippet() == null) ? 0 : -1;
        // }
        // if (this.snippet == null) {
        //     log.info(">>**********************");
        //     log.info(this.snippet + "==" + o.getSnippet());
        //     log.info(this.snippet.split("/")[0] + "==" + o.getSnippet().split("/")[0]);
        //     log.info(">>**********************");
        //     return 1;
        // }
        // TODO Auto-generated method stub
        return Integer.parseInt(this.snippet.split("/")[0]) - Integer.parseInt(o.getSnippet().split("/")[0]); 

    }
    
}
