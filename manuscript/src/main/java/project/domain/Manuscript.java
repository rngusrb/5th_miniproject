package project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import project.ManuscriptApplication;
import project.domain.AiSummaryRequested;
import project.domain.ManuscriptDeleted;
import project.domain.ManuscriptEdited;
import project.domain.ManuscriptSubmitted;
import project.domain.PublicationRequested;

@Entity
@Table(name = "Manuscript_table")
@Data
//<<< DDD / Aggregate Root
public class Manuscript {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long manuscriptId;

    private String authorId;

    private String title;

    private String category;

    private String content;

    private Date createDate;

    private Date modifyDate;

    private String summary;

    private String bookCoverImage;

    @PostPersist
    public void onPostPersist() {
        ManuscriptSubmitted manuscriptSubmitted = new ManuscriptSubmitted(this);
        manuscriptSubmitted.publishAfterCommit();

        ManuscriptEdited manuscriptEdited = new ManuscriptEdited(this);
        manuscriptEdited.publishAfterCommit();

        ManuscriptDeleted manuscriptDeleted = new ManuscriptDeleted(this);
        manuscriptDeleted.publishAfterCommit();

        AiSummaryRequested aiSummaryRequested = new AiSummaryRequested(this);
        aiSummaryRequested.publishAfterCommit();

        PublicationRequested publicationRequested = new PublicationRequested(
            this
        );
        publicationRequested.publishAfterCommit();
    }

    public static ManuscriptRepository repository() {
        ManuscriptRepository manuscriptRepository = ManuscriptApplication.applicationContext.getBean(
            ManuscriptRepository.class
        );
        return manuscriptRepository;
    }
}
//>>> DDD / Aggregate Root
