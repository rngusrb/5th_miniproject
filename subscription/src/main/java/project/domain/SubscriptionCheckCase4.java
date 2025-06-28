package project.domain;

import lombok.Data;
import lombok.ToString;
import project.infra.AbstractEvent;

@Data @ToString
public class SubscriptionCheckCase4 extends AbstractEvent {
    // 구독권 x, 구독 X
    private Long bookId;

    public SubscriptionCheckCase4(Long bookId) { 
        super();
        this.bookId = bookId;
        
    }
}