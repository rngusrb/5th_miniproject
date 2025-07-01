package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscriptionNotOwned extends AbstractEvent {

    private Long id;

    public SubscriptionNotOwned(Subscription aggregate) {
        super(aggregate);
    }

    public SubscriptionNotOwned() {
        super();
    }
}
//>>> DDD / Domain Event
