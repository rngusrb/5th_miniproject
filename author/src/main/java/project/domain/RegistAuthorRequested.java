package project.domain;

import lombok.*;
import project.domain.Author;
import project.infra.AbstractEvent;

@Data
@ToString
public class RegistAuthorRequested extends AbstractEvent {

    private Long id;
    private Long authorId;

    public RegistAuthorRequested(Author aggregate) {
        super(aggregate);
        this.id = aggregate.getAuthorId();
        this.authorId = aggregate.getAuthorId(); // ✅ 이 줄 추가
    }

    public RegistAuthorRequested() {
        super();
    }
}
//>>> DDD / Domain Event
