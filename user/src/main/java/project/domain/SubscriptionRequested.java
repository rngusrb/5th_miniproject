package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscriptionRequested extends AbstractEvent {

    private Long id;

    public SubscriptionRequested(User aggregate) {
        super(aggregate);
    }

    public SubscriptionRequested() {
        super();
    }
}
//>>> DDD / Domain Event
