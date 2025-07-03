package project.domain;

import lombok.*;
import project.infra.AbstractEvent;
import java.util.Date;

@Data
@ToString
public class RegistAuthorRequested extends AbstractEvent {

    private Long id;
    private String authorLoginId;
    private String authorPw;
    private String authorName;
    private Date createDate;
    private String authorInfo;
    private String authorPortfolio;
    private Boolean isActive;

    public RegistAuthorRequested(Author aggregate) {
        super(aggregate);
        this.id = aggregate.getAuthorId();
        this.authorLoginId = aggregate.getAuthorLoginId();
        this.authorPw = aggregate.getAuthorPw();
        this.authorName = aggregate.getAuthorName();
        this.createDate = aggregate.getCreateDate();
        this.authorInfo = aggregate.getAuthorInfo();
        this.authorPortfolio = aggregate.getAuthorPortfolio();
        this.isActive = aggregate.getIsActive();
    }

    public RegistAuthorRequested() {
        super();
    }
}
//>>> DDD / Domain Event
