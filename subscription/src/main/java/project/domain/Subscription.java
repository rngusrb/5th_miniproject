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

import org.springframework.web.client.RestTemplate;
import project.infra.SubscriptionListRepository;

@Entity
@Table(name = "Subscription_table")
@Data
//<<< DDD / Aggregate Root
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private Long userId;
    private Long bookId;

    @PostPersist
    public void onPostPersist() {
        SubscriptionSaved event = new SubscriptionSaved(this);
        System.out.println("✅ SubscriptionSaved 발행 시도: " + this);
        event.publishAfterCommit();
    }

    
    public static SubscriptionRepository repository() {
        return SubscriptionApplication.applicationContext.getBean(SubscriptionRepository.class);
    }

    // 도메인 책임: 구독 저장 + 이벤트 발행
    public static void addSubscription(Long userId, Long bookId) {
        SubscriptionRepository repo = repository();

        // 중복 구독 방지 (선택적)
        boolean exists = repo.existsByUserIdAndBookId(userId, bookId);
        if (!exists) {
            Subscription sub = new Subscription();
            sub.setUserId(userId);
            sub.setBookId(bookId);
            repo.save(sub);

            // 이벤트 발행은 @PostPersist로 자동
            System.out.println("Subscription 저장 완료 및 이벤트 발행 요청");
        } else {
            System.out.println("이미 구독된 항목입니다: userId=" + userId + ", bookId=" + bookId);
        }
    }

    // 기타 도메인 로직 생략
}
