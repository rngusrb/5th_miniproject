package project.domain;

import lombok.Data;
import lombok.ToString;
import project.infra.AbstractEvent;

@Data @ToString
public class SubscriptionCheckCase1 extends AbstractEvent {
    // 구독권 O, 구독 x
    private Long userId;
    private Long bookId;
    private Long subscriptionType;

    public SubscriptionCheckCase1(Subscription sub) { 
        super(sub);
        this.userId = sub.getUserId();
        this.bookId = sub.getBookId();
        this.subscriptionType = sub.getSubscriptionType();
     }
}