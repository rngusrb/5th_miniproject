package project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import project.AuthorApplication;
import project.domain.AuthorDeleted;
import project.domain.AuthorModified;
import project.domain.RegistAuthorRequested;

@Entity
@Table(name = "Author_table")
@Data
//<<< DDD / Aggregate Root
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

//    @PostPersist
//    public void onPostPersist() {
//        RegistAuthorRequested registAuthorRequested = new RegistAuthorRequested(
//            this
//        );
//        registAuthorRequested.publishAfterCommit();
//
//        AuthorModified authorModified = new AuthorModified(this);
//        authorModified.publishAfterCommit();
//
//        AuthorDeleted authorDeleted = new AuthorDeleted(this);
//        authorDeleted.publishAfterCommit();
//    }
//
//    @PostUpdate
//    public void onPostUpdate() {
//    AuthorModified authorModified = new AuthorModified(this);
//    authorModified.publishAfterCommit();
//   }
//
//    @PostRemove
//    public void onPostRemove() {
//    AuthorDeleted authorDeleted = new AuthorDeleted(this);
//    authorDeleted.publishAfterCommit();
//    }

    public void register() {
        RegistAuthorRequested registAuthorRequested = new RegistAuthorRequested(this);
        registAuthorRequested.publishAfterCommit();
    }

    public void signup() {
        AuthorRegistered authorRegistered = new AuthorRegistered(this);
        authorRegistered.publishAfterCommit();
    }

    public void login() {
        AuthorLoggedIn authorLoggedIn = new AuthorLoggedIn(this);
        authorLoggedIn.publishAfterCommit();
    }

    public static AuthorRepository repository() {
        AuthorRepository authorRepository = AuthorApplication.applicationContext.getBean(
            AuthorRepository.class
        );
        return authorRepository;
    }
}
//>>> DDD / Aggregate Root
