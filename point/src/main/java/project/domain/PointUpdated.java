package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PointUpdated extends AbstractEvent {

    private Long id;

    public PointUpdated(Point aggregate) {
        super(aggregate);
    }

    public PointUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
