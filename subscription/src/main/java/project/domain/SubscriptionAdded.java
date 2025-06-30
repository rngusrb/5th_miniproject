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

    // 구독 추가 이벤트 !
    public SubscriptionAdded(Subscription aggregate) {
        super(aggregate);

        this.userId = aggregate.getUserId();
        this.bookId = aggregate.getBookId();
        
    }

    public SubscriptionAdded() {
        super();
    }
}
//>>> DDD / Domain Event
