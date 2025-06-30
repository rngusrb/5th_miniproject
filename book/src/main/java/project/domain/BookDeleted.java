package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class BookDeleted extends AbstractEvent {

    private Long id;

    public BookDeleted(Book aggregate) {
        super(aggregate);
    }

    public BookDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
