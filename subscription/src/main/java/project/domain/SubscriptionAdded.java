package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscriptionAdded extends AbstractEvent {

    private Long userId;
    private Long bookId;

    public SubscriptionAdded(Subscription aggregate) {
        super(aggregate);
    }

    public SubscriptionAdded() {
        super();
    }
}
//>>> DDD / Domain Event
