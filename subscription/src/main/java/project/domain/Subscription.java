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
        event.publishAfterCommit();
    }

    // 기타 도메인 로직 생략
}
