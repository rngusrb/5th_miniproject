package project.domain.events;

import lombok.Data;
import lombok.ToString;
import project.infra.AbstractEvent;
import project.domain.Subscription;



// 요금제 9,900원시 발생되는 Event!!
@Data
@ToString
public class SubscriptionUnlimitedAccess extends AbstractEvent {
    private Long userId;
    private Long bookId;
    private Long subscriptionType;

    public SubscriptionUnlimitedAccess(Subscription sub) {
        super(sub);
        this.userId           = sub.getUserId();
        this.bookId           = sub.getBookId();
        this.subscriptionType = sub.getSubscriptionType();
    }
}