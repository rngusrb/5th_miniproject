package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class NewPointAwarded extends AbstractEvent {

    private Long id;

    public NewPointAwarded(Platform aggregate) {
        super(aggregate);
    }

    public NewPointAwarded() {
        super();
    }
}
//>>> DDD / Domain Event
