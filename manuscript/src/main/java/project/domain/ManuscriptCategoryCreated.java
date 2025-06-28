package project.domain;

import lombok.Data;
import lombok.ToString;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ManuscriptCategoryCreated extends AbstractEvent {

    private Long manuscriptId;
    private String content;

    public ManuscriptCategoryCreated(Manuscript aggregate) {
        super(aggregate);
    }

    public ManuscriptCategoryCreated() {
        super();
    }
}
//>>> DDD / Domain Event
