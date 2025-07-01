package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscriptionOwned extends AbstractEvent {

    private Long id;

    public SubscriptionOwned(Subscription aggregate) {
        super(aggregate);
    }

    public SubscriptionOwned() {
        super();
    }
}
//>>> DDD / Domain Event
