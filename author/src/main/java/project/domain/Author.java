package project.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;
import project.AuthorApplication;

@Data
@Entity
@Table(name = "Author_table")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long authorId;

    private String authorLoginId;
    private String authorPw;
    private String authorName;
    private Date createDate;
    private String authorInfo;
    private String authorPortfolio;
    private Boolean isActive;

    public void register() {
        RegistAuthorRequested event = new RegistAuthorRequested(this);
        event.publishAfterCommit();
    }

    public void signup() {
        AuthorRegistered event = new AuthorRegistered(this);
        event.publishAfterCommit();
    }

    public void login() {
        AuthorLoggedIn event = new AuthorLoggedIn(this);
        event.publishAfterCommit();
    }

    public static AuthorRepository repository() {
        return AuthorApplication.applicationContext.getBean(AuthorRepository.class);
    }
}

