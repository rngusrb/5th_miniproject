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
        this.setEventType("SubscriptionSaved");
        System.out.println("✅ SubscriptionSaved 객체 생성 완료: userId=" + userId + ", bookId=" + bookId); // 🔍

    }

    public SubscriptionSaved() {
        super();
    }
}
