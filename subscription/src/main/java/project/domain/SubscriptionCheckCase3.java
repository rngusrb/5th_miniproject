package project.domain;

import lombok.Data;
import lombok.ToString;
import project.infra.AbstractEvent;

@Data @ToString
public class SubscriptionCheckCase3 extends AbstractEvent {
    // 구독권 x, 구독 o
    private Long userId;
    private Long bookId;
    private Long subscriptionType;
    private Integer point;
    
    public SubscriptionCheckCase3(Subscription sub) { 
        super(sub);
        this.userId = sub.getUserId();
        this.bookId = sub.getBookId();
        this.subscriptionType = sub.getSubscriptionType();
    }
}