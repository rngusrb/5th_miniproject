package project.domain;

import lombok.*;
import project.infra.AbstractEvent;

@Data
@ToString
public class AuthorModified extends AbstractEvent {

    private Long id;

    public AuthorModified(Author aggregate) {
        super(aggregate);
        this.id = aggregate.getAuthorId();
    }

    public AuthorModified() {
        super();
    }
}
//>>> DDD / Domain Event
