package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ManuscriptDeleted extends AbstractEvent {

    private Long id;

    public ManuscriptDeleted(Manuscript aggregate) {
        super(aggregate);
    }

    public ManuscriptDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
