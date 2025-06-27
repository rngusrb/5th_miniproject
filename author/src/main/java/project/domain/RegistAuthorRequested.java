package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class RegistAuthorRequested extends AbstractEvent {

    private Long id;

    public RegistAuthorRequested(Author aggregate) {
        super(aggregate);
    }

    public RegistAuthorRequested() {
        super();
    }
}
//>>> DDD / Domain Event
