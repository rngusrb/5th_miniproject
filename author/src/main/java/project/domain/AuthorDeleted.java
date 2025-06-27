package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class AuthorDeleted extends AbstractEvent {

    private Long id;

    public AuthorDeleted(Author aggregate) {
        super(aggregate);
    }

    public AuthorDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
