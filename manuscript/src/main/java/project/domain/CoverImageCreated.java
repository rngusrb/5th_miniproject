package project.domain;

import lombok.Data;
import lombok.ToString;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class CoverImageCreated extends AbstractEvent {

    private Long manuscriptId;
    private String content;

    public CoverImageCreated(Manuscript aggregate) {
        super(aggregate);
    }

    public CoverImageCreated() {
        super();
    }
}
//>>> DDD / Domain Event
