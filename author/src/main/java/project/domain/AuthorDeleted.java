package project.domain;

import lombok.*;
import project.infra.AbstractEvent;

@Data
@ToString
public class AuthorDeleted extends AbstractEvent {

    private Long Id;

    public AuthorDeleted(Author aggregate) {
        super(aggregate);
        this.Id = aggregate.getAuthorId();
    }

    public AuthorDeleted() {
        super();
    }
}
//>>> DDD / Domain Event
