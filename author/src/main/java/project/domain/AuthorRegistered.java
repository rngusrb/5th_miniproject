package project.domain;

import lombok.Data;
import lombok.ToString;
import project.infra.AbstractEvent;

@Data
@ToString
public class AuthorRegistered extends AbstractEvent {
    private Long id;
    private String authorLoginId;
    private String authorPw;

    public AuthorRegistered(Author aggregate) {
        super(aggregate);
        this.id = aggregate.getAuthorId();
        this.authorLoginId = aggregate.getAuthorLoginId();
        this.authorPw = aggregate.getAuthorPw();
    }

    public AuthorRegistered() {
        super();
    }
}
