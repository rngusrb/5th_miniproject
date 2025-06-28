package project.domain;

import lombok.Data;
import lombok.ToString;
import project.infra.AbstractEvent;

@Data @ToString
public class SubscriptionCheckCase2 extends AbstractEvent {
    // 구독권 O, 구독 o
    private Long userId;
    private Long bookId;
    private Integer point;
    
    public SubscriptionCheckCase2(Subscription sub) { 
        super(sub);
        this.userId = sub.getUserId();
        this.bookId = sub.getBookId();
        this.subscriptionType = sub.getSubscriptionType();


    }
}