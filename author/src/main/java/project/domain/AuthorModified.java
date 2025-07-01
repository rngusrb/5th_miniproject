package project.domain;

import lombok.*;
import project.infra.AbstractEvent;

@Data
@ToString
public class AuthorModified extends AbstractEvent {

    private Long id;
    private String authorName;
    private String authorInfo;
    private String authorPortfolio;
    private Boolean isActive;
    private String authorPw;

    public AuthorModified(Author aggregate) {
        super(aggregate);
        this.id = aggregate.getAuthorId();  // authorId가 아니라 id
        this.authorName = aggregate.getAuthorName();
        this.authorInfo = aggregate.getAuthorInfo();
        this.authorPortfolio = aggregate.getAuthorPortfolio();
        this.isActive = aggregate.getIsActive();
        this.authorPw = aggregate.getAuthorPw();
    }

    public AuthorModified() {
        super();
    }
}
//>>> DDD / Domain Event
