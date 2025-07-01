package project.domain;

import java.time.LocalDateTime;
import lombok.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PublicationRequested extends AbstractEvent {

    private Long manuscriptId;

    private Long authorId;

    private String title;

    private String category;

    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private String summary;

    private String bookCoverImage;

    private String status;

    public PublicationRequested(Manuscript aggregate) {
        super(aggregate);
    }

    public PublicationRequested() {
        super();
    }

}
//>>> DDD / Domain Event
