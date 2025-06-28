package project.domain;

import lombok.Data;
import lombok.ToString;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ManuscriptSummary extends AbstractEvent {

    private Long manuscriptId;
    private String content;

    public ManuscriptSummary(Manuscript aggregate) {
        super(aggregate);
    }

    public ManuscriptSummary() {
        super();
    }
}
//>>> DDD / Domain Event
