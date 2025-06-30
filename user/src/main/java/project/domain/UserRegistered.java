package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class UserRegistered extends AbstractEvent {
    private Long userId;

    public UserRegistered(User aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
        this.setEventType(this.getClass().getSimpleName());
    }
}
//>>> DDD / Domain Event
