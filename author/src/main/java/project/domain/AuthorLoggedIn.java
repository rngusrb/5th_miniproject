package project.domain;

import lombok.Data;
import lombok.ToString;
import project.infra.AbstractEvent;

@Data
@ToString
public class AuthorLoggedIn extends AbstractEvent {
    private Long Id;
    private String authorLoginId;
    private String authorPw;

    public AuthorLoggedIn(Author aggregate) {
        super(aggregate);
        this.Id = aggregate.getAuthorId();
        this.authorLoginId = aggregate.getAuthorLoginId();
        this.authorPw = aggregate.getAuthorPw();
    }

    public AuthorLoggedIn() {
        super();
    }
}
