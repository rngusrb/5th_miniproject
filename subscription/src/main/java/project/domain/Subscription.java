package project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.SubscriptionApplication;
import project.domain.BookAccessGranted;

@Entity
@Table(name = "Subscription_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
//<<< DDD / Aggregate Root
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    private Long userId;
    private Long bookId;

    public static SubscriptionRepository repository() {
        SubscriptionRepository subscriptionRepository = SubscriptionApplication.applicationContext.getBean(
            SubscriptionRepository.class
        );
        return subscriptionRepository;
    }

    //<<< Clean Arch / Port Method
    public static void subscriptionSuccess(BookAccessGranted bookAccessGranted, Long bookId) {
        //implement business logic here:
        boolean exists = repository().existsByUserIdAndBookId(
            bookAccessGranted.getUserId(),
            // bookAccessGranted.getBookId()
            bookId
        );

        if (!exists) {
            Subscription subscription = new Subscription();
            subscription.setUserId(bookAccessGranted.getUserId());
            subscription.setBookId(bookId);

            repository().save(subscription);
            System.out.println("✅ 구독 성공 저장 완료 (subscriptionSuccess)");
        } else {
            System.out.println("⚠️ 이미 구독되어 있음");
        }


        /** Example 1:  new item 
        Subscription subscription = new Subscription();
        repository().save(subscription);

        */

        /** Example 2:  finding and process
        

        repository().findById(passOwned.get???()).ifPresent(subscription->{
            
            subscription // do something
            repository().save(subscription);


         });
        */

    }
    //>>> Clean Arch / Port Method

    public static void subscriptionAdd(PointUpdated pointUpdated, Long bookId) {
        // 중복 체크 생략 가능하거나 포함 가능
        Subscription subscription = new Subscription();
        subscription.setUserId(pointUpdated.getUserId());
        subscription.setBookId(bookId);

        repository().save(subscription);

        SubscriptionAdded event = new SubscriptionAdded(subscription);
        event.publishAfterCommit();

        System.out.println("✅ 구독 추가 및 이벤트 발행 완료 (subscriptionAdd)");
    }



}
//>>> DDD / Aggregate Root
