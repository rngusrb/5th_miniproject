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


//-------------------------------------------------------------------------------------------------------
@Entity
@Table(name = "Subscription_table")
@Data
//<<< DDD / Aggregate Root
public class Subscription {

    // 사용자 id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    // 포인트
    private Integer point;

    // 구독 책 id
    private Long bookId;

    // 구독권 유형 (요금제) -> 9,900 | 그 외 요금제~ (추후 토의하면서 추가 구현) 
    private Long subscriptionType;

//-------------------------------------------------------------------------------------------------------

    @PostPersist
    public void onPostPersist() {
         
        // 구독 목록에 추가될 새 레코드 정보를 담은 객체를 생성
        SubscriptionList ev = new SubscriptionList(this);
        // kafka 내부 이벤트 버스에 트랜잭션 커밋 후 전송!
        ev.publishAfterCommit();
    }

    public static SubscriptionRepository repository() {
        SubscriptionRepository subscriptionRepository = SubscriptionApplication.applicationContext.getBean(
            SubscriptionRepository.class
        );
        return subscriptionRepository;
    }
//-------------------------------------------------------------------------------------------------------
    // 구독권/구독 확인 -> Policy
    public static void subscriptionCheck(BookViewed bookViewed) {

        // bookId 가져와서 Subscription domain에 저장
        Long bookId = bookViewed.getId();
        
        repository().findByBookId(bookId).ifPresentOrElse(

        // 열람 요청한 사용자가 이미 해당 도서를 가지고 있는 일때!
         sub -> {

        // 1) 9900 요금제 → 무제한 액세스 (포인트 변동 필요없음)
        if (Long.valueOf(9900).equals(sub.getSubscriptionType())) {
            SubscriptionUnlimitedAccess ev = 
                new SubscriptionUnlimitedAccess(sub);
            ev.publishAfterCommit();
            return;   // 무제한 열람이므로 그 후 로직 x
        }       

        boolean hasSubscription = sub.getSubscriptionType() != null && sub.getSubscriptionType() > 0;
        boolean hasBook         = sub.getPoint()            != null && sub.getPoint()            > 0;

        // 구독권 o , 구독 x
        if (hasSubscription && !hasBook) {
            SubscriptionCheckCase1 ev = new SubscriptionCheckCase1(sub);
            ev.publishAfterCommit();

        // 구독권 x , 구독 o
        } else if (!hasSubscription && hasBook) {
            SubscriptionCheckCase2 ev = new SubscriptionCheckCase2(sub);
            ev.publishAfterCommit();

        // 구독권 o , 구독 o
        } else if (hasSubscription && hasBook) {
            SubscriptionCheckCase3 ev = new SubscriptionCheckCase3(sub);
            ev.publishAfterCommit();

        // 구독권 x , 구독 x
        } else {
            SubscriptionCheckCase4 ev = new SubscriptionCheckCase4(bookId);
            ev.publishAfterCommit();
        }

    }, () -> {
        // bookId 가 아예 없을 때! (기존 구독 목록 리스트에 없을 때!)
        SubscriptionCheckCase4 ev = new SubscriptionCheckCase4(bookId);
        ev.publishAfterCommit();
         });
        */

    }

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

}
//>>> DDD / Aggregate Root
