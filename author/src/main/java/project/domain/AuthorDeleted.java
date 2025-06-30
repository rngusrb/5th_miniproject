package project.domain;

import lombok.*;
import project.infra.AbstractEvent;

@Data
@ToString
public class AuthorDeleted extends AbstractEvent {

    private Long id;

    public AuthorDeleted(Author aggregate) {
        super(aggregate);
        this.id = aggregate.getAuthorId();
    }

    public AuthorDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
