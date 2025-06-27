package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class GetViewCount extends AbstractEvent {

    private Long id;

    public GetViewCount(Book aggregate) {
        super(aggregate);
    }

    public GetViewCount() {
        super();
    }
}
//>>> DDD / Domain Event
