package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class ManuscriptEdited extends AbstractEvent {

    private Long id;

    public ManuscriptEdited(Manuscript aggregate) {
        super(aggregate);
    }

    public ManuscriptEdited() {
        super();
    }
}
//>>> DDD / Domain Event
