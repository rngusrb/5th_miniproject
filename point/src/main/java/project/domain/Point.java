package project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import project.PointApplication;
import project.domain.PointUpdated;
import project.domain.events.SubscriptionCheckCase4;

@Entity
@Table(name = "Point_table")
@Data
//<<< DDD / Aggregate Root
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private Integer point;

    private Date changeDate;

    private Date expireDate;

    private Integer changePoint;

    private Integer remainPoint;

    @PostPersist
    public void onPostPersist() {
        PointUpdated pointUpdated = new PointUpdated(this);
        pointUpdated.publishAfterCommit();
    }

    public static PointRepository repository() {
        PointRepository pointRepository = PointApplication.applicationContext.getBean(
            PointRepository.class
        );
        return pointRepository;
    }

    // 포인트 사용 메서드
    // 구독권도 없고, 열람 요청한 책에 대한 구독 이력이 없을 때!
    public static void pointBalanceChange(SubscriptionCheckCase4 evt) {
        Long userId = evt.getUserId();
        Long bookId = evt.getBookId();
        final int cost = 1000; // 예시: 열람 비용

        repository().findById(userId).ifPresent(p -> {
            int remaining = (p.getRemainPoint() != null ? p.getRemainPoint() : p.getPoint());

            if (remaining >= cost) {
                // 포인트 차감
                p.setChangePoint(-cost);
                p.setRemainPoint(remaining - cost);
                p.setChangeDate(new Date());
                repository().save(p);
                // save 시 @PostPersist가 아니므로 수동 이벤트 발행
                PointUpdated upd = new PointUpdated(p);
                upd.publishAfterCommit();

            } else {
                // 포인트 부족 시 안내
                System.out.println("포인트가 부족합니다. KT 통신사 이동 및 요금제 구독을 추천드립니다.");
            }
        });       

    }
}

