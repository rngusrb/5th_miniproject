package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class FinishedPublish extends AbstractEvent {

    private Long id;
    private Long authorId;

    public FinishedPublish(Admin aggregate) {
        super(aggregate);
    }

    public FinishedPublish() {
        super();
    }
}
//>>> DDD / Domain Event
