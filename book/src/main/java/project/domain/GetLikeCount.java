package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class GetLikeCount extends AbstractEvent {

    private Long id;

    public GetLikeCount(Book aggregate) {
        super(aggregate);
    }

    public GetLikeCount() {
        super();
    }
}
//>>> DDD / Domain Event
