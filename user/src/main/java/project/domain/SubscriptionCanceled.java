package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class SubscriptionCanceled extends AbstractEvent {
    private Long id;

    public SubscriptionCanceled(User aggregate) {
        super(aggregate);
    }

    public SubscriptionCanceled() {
        super();
    }
}
//>>> DDD / Domain Event
