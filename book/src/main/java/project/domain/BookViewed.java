package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class BookViewed extends AbstractEvent {

    private Long id;

    public BookViewed(Book aggregate) {
        super(aggregate);
    }

    public BookViewed() {
        super();
    }
}
//>>> DDD / Domain Event
