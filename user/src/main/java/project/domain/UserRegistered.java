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
    private Boolean isKtMember; 

    public UserRegistered(User aggregate) {
        super(aggregate);
        this.userId = aggregate.getUserId();
        this.isKtMember = aggregate.getIsKtMember();
        this.setEventType(this.getClass().getSimpleName());
        
    }
    public UserRegistered(Long userId, Boolean isKtMember) {
        super();
        this.userId = userId;
        this.isKtMember = isKtMember;
        this.setEventType(this.getClass().getSimpleName());
    }
}
//>>> DDD / Domain Event
