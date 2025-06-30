package project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import project.SubscriptionApplication;
import project.domain.SubscriptionAdded;

@Entity
@Table(name = "Subscription_table")
@Data
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private Long bookId;

    public static SubscriptionRepository repository() {
        SubscriptionRepository subscriptionRepository = SubscriptionApplication.applicationContext.getBean(
            SubscriptionRepository.class
        );
        return subscriptionRepository;
    }

    //<<< Clean Arch / Port Method
    public static void subscriptionSuccess(
        BookAccessGranted bookAccessGranted
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Subscription subscription = new Subscription();
        repository().save(subscription);

        */

        /** Example 2:  finding and process
        

        repository().findById(bookAccessGranted.get???()).ifPresent(subscription->{
            
            subscription // do something
            repository().save(subscription);


         });
        */

    }

    // 구독 추가 
    public static void subscriptionAdd(PointMinus pointMinus) {
        // 새로운 구독 엔티티 생성
        Subscription subscription = new Subscription();
        subscription.setUserId(pointMinus.getUserId());
        subscription.setBookId(pointMinus.getBookId());

        // DB에 저장
        repository().save(subscription);

        // 도메인 이벤트 발행
        SubscriptionAdded subscriptionAdded = new SubscriptionAdded(subscription);
        // (필요하다면 이벤트 객체에 추가 필드 셋팅)
        subscriptionAdded.setUserId(subscription.getUserId());
        subscriptionAdded.setBookId(subscription.getBookId());
        // 다음 이벤트로 가도록 해줌
        subscriptionAdded.publishAfterCommit();

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
