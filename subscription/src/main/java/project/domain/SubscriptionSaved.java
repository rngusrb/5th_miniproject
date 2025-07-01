package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;


@Data
@ToString
public class SubscriptionSaved extends AbstractEvent {

    private Long userId;
    private Long bookId;

    public SubscriptionSaved(Subscription subscription) {
        super(subscription);
        this.userId = subscription.getUserId();
        this.bookId = subscription.getBookId();
    }

    public SubscriptionSaved() {
        super();
    }
}
