package project.domain;

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
import project.domain.SubscriptionRequested;
import project.domain.UserLoggedIn;
import project.domain.UserRegistered;

@Entity
@Table(name = "User_table")
@Data
//<<< DDD / Aggregate Root
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private Long userPw;

    private Long point;

    @PostPersist
    public void onPostPersist() {
        UserRegistered userRegistered = new UserRegistered(this);
        userRegistered.publishAfterCommit();

        UserLoggedIn userLoggedIn = new UserLoggedIn(this);
        userLoggedIn.publishAfterCommit();

        SubscriptionRequested subscriptionRequested = new SubscriptionRequested(
            this
        );
        subscriptionRequested.publishAfterCommit();

        SubscriptionCanceled subscriptionCanceled = new SubscriptionCanceled(
            this
        );
        subscriptionCanceled.publishAfterCommit();
    }

    public static UserRepository repository() {
        UserRepository userRepository = UserApplication.applicationContext.getBean(
            UserRepository.class
        );
        return userRepository;
    }

    //<<< Clean Arch / Port Method
    public void reqeustLogin() {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void requestUserRegistration() {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void requestSubscription() {
        //implement business logic here:

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void cancelSubscription() {
        //implement business logic here:

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
