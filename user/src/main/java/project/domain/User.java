package project.domain;
import java.util.Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import project.UserApplication;
import project.domain.SubscriptionCanceled;
import project.domain.UserLoggedIn;
import project.domain.UserRegistered;

@Entity
@Table(name = "User_table")
@Data
//<<< DDD / Aggregate Root
public class User {

    @Id
    private Long userId;

    private Long userPw;
    private Boolean pass;

    @PostPersist
    public void onPostPersist() {
    }

    public static UserRepository repository() {
        UserRepository userRepository = UserApplication.applicationContext.getBean(
            UserRepository.class
        );
        return userRepository;
    }

    //<<< Clean Arch / Port Method
    public void login(Long inputPw) {
        if (!Objects.equals(this.userPw, inputPw)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        UserLoggedIn event = new UserLoggedIn(this);
        event.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void requestUserRegistration() {
        UserRegistered userRegistered = new UserRegistered(this);
        userRegistered.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void requestSubscription() {
        this.pass = true;
        SubscriptionRequested event = new SubscriptionRequested(this);
        event.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void cancelSubscription() {
        this.pass = false;
        SubscriptionCanceled event = new SubscriptionCanceled(this);
        event.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

    public void checkBookAccess(Long bookId) {
        if (Boolean.TRUE.equals(this.pass)) {
            BookAccessGranted event = new BookAccessGranted(this.getUserId(), bookId);
            event.publishAfterCommit();
        } else {
            BookAccessDenied event = new BookAccessDenied(this.getUserId(), bookId);
            event.publishAfterCommit();
        }
    }    

}
//>>> DDD / Aggregate Root
