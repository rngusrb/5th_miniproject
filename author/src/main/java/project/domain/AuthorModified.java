package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class AuthorModified extends AbstractEvent {

    private Long id;

    public AuthorModified(Author aggregate) {
        super(aggregate);
    }

    public AuthorModified() {
        super();
    }
}
//>>> DDD / Domain Event
