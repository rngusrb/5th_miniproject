package project.domain;

import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

@Data
@ToString
public class UserRegistered extends AbstractEvent {
    private Long userId;
    private Boolean isKtMember;

    // ✅ Kafka 역직렬화를 위한 기본 생성자
    public UserRegistered() {
        super();
    }

    public UserRegistered(Long userId, Boolean isKtMember) {
        this.userId = userId;
        this.isKtMember = isKtMember;
        this.setEventType(this.getClass().getSimpleName());
    }
}