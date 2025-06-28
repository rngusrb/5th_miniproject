package project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.Table;

import lombok.Data;
import project.SubscriptionApplication;
import project.domain.events.SubscriptionCheckCase1;
import project.domain.events.SubscriptionCheckCase2;
import project.domain.events.SubscriptionCheckCase3;
import project.domain.events.SubscriptionCheckCase4;
import project.domain.events.BookViewed;

@Entity
@Table(name = "Subscription_table")
@Data
//<<< DDD / Aggregate Root
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    // 포인트
    private Integer point;

    // 구독 책 id
    private Long bookId;

    // 구독권 유형 (요금제) 
    private Long subscriptionType;



    @PostPersist
    public void onPostPersist() {
        SubscriptionOwned subscriptionOwned = new SubscriptionOwned(this);
        subscriptionOwned.publishAfterCommit();

        SubscriptionNotOwned subscriptionNotOwned = new SubscriptionNotOwned(
            this
        );
        subscriptionNotOwned.publishAfterCommit();
    }

    public static SubscriptionRepository repository() {
        SubscriptionRepository subscriptionRepository = SubscriptionApplication.applicationContext.getBean(
            SubscriptionRepository.class
        );
        return subscriptionRepository;
    }

    //<<< Clean Arch / Port Method
    public static void subscriptionCheck(BookViewed bookViewed) {
        Long bookId = bookViewed.getId();

        repository().findByBookId(bookId).ifPresentOrElse(sub -> {


        // 1) 9900 요금제 → 무제한 액세스 (포인트 변동 필요없음)
        if (Long.valueOf(9900).equals(sub.getSubscriptionType())) {
            SubscriptionUnlimitedAccess ev = 
                new SubscriptionUnlimitedAccess(sub);
            ev.publishAfterCommit();
            return;   // 이후 로직 실행하지 않음
        }       

        boolean hasVoucher    = sub.getSubscriptionType() != null && sub.getSubscriptionType() > 0;
        boolean hasSubscribed = sub.getPoint()            != null && sub.getPoint() > 0;

        // 구독권 o , 구독 x
        if (hasVoucher && !hasSubscribed) {
            SubscriptionCheckCase1 ev = new SubscriptionCheckCase1(sub);
            ev.publishAfterCommit();

        // 구독권 o , 구독 o
        } else if (!hasVoucher && hasSubscribed) {
            SubscriptionCheckCase2 ev = new SubscriptionCheckCase2(sub);
            ev.publishAfterCommit();

        // 구독권 x , 구독 o
        } else if (hasVoucher && hasSubscribed) {
            SubscriptionCheckCase3 ev = new SubscriptionCheckCase3(sub);
            ev.publishAfterCommit();

        // 구독권 x , 구독 x
        } else {
            SubscriptionCheckCase4 ev = new SubscriptionCheckCase4(bookId);
            ev.publishAfterCommit();
        }

    }, () -> {
        // 레코드조차 없으면 (구독권 X, 구독 X)
        SubscriptionCheckCase4 ev = new SubscriptionCheckCase4(bookId);
        ev.publishAfterCommit();
         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void subscriptionAdd(PointUpdated pointUpdated) {
        //implement business logic here:

        /** Example 1:  new item 
        Subscription subscription = new Subscription();
        repository().save(subscription);

        */

        /** Example 2:  finding and process
        

        repository().findById(pointUpdated.get???()).ifPresent(subscription->{
            
            subscription // do something
            repository().save(subscription);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
