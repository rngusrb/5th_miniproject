package project.domain;

import lombok.Data;
import lombok.ToString;
import project.infra.AbstractEvent;

@Data
@ToString
public class AuthorLoggedIn extends AbstractEvent {
    private Long id;
    private String authorLoginId;
    private String authorPw;

    public AuthorLoggedIn(Author aggregate) {
        super(aggregate);
        this.id = aggregate.getAuthorId();
        this.authorLoginId = aggregate.getAuthorLoginId();
        this.authorPw = aggregate.getAuthorPw();
    }

    public AuthorLoggedIn() {
        super();
    }
}
