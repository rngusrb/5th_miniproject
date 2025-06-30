package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PointMinus extends AbstractEvent {

    private Long id;

    public PointMinus(Point aggregate) {
        super(aggregate);
    }

    public PointMinus() {
        super();
    }
}
//>>> DDD / Domain Event
