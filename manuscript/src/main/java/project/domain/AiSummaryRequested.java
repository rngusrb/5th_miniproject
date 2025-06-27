package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class AiSummaryRequested extends AbstractEvent {

    private Long id;

    public AiSummaryRequested(Manuscript aggregate) {
        super(aggregate);
    }

    public AiSummaryRequested() {
        super();
    }
}
//>>> DDD / Domain Event
