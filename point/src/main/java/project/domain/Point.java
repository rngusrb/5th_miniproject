package project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.PointApplication;
import project.domain.PointUpdated;

@Entity
@Table(name = "Point_table")
@Data
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private Long userId;

    private Integer point;

    private Date changeDate;

    private Date expireDate;

    private Integer changePoint;

    private Long pointSum;

    private String reason;

    public static PointRepository repository() {
        return PointApplication.applicationContext.getBean(PointRepository.class);
    }

    // 포인트 차감: 도서 접근 거부 시
public static void pointBalanceChange(BookAccessDenied bookAccessDenied) {
    Long userId = bookAccessDenied.getUserId();
    Long bookId = bookAccessDenied.getBookId();
    int requiredPoint = 500;

    // 가장 최근 포인트 합계 조회
    Long currentSum = 0L;
    Point latest = repository().findLatestByUserId(userId); // native 쿼리 기준
    if (latest != null) {
        currentSum = latest.getPointSum();
    }

    // 차감 가능할 경우
    if (currentSum >= requiredPoint) {
        Point point = new Point();
        point.setUserId(userId);
        point.setChangeDate(new Date());
        point.setChangePoint(-requiredPoint);
        point.setPointSum(currentSum - requiredPoint);
        point.setReason("책 접근 시 포인트 차감");

        repository().save(point);

        PointMinus pointMinus = new PointMinus(userId, bookId);
        pointMinus.publishAfterCommit();

    } else {
        System.out.println("포인트 부족: userId=" + userId + ", 현재 잔액=" + currentSum);
        // PointNotEnough 이벤트 발행 같은 것도 여기에 작성 가능
    }
}
public static void pointBalanceChange(UserRegistered userRegistered) {
    Long userId = userRegistered.getUserId();

    // 지급할 포인트 설정
    int grantPoint = 1000;
    // if ("kt".equalsIgnoreCase(userRegistered.getUserType())) {
    //     grantPoint = 5000;
    // }

    // 현재 누적 포인트 조회 (최신 1건)
    Long currentSum = 0L;
    Point latest = repository().findLatestByUserId(userId);
    if (latest != null) {
        currentSum = latest.getPointSum();
    }

    // 새 포인트 로그 생성
    Point point = new Point();
    point.setUserId(userId);
    point.setChangeDate(new Date());
    point.setChangePoint(grantPoint);
    point.setPointSum(currentSum + grantPoint);
    point.setReason("Welcome Bonus");

    repository().save(point);

    PointUpdated pointGranted = new PointUpdated(point);
    pointGranted.publishAfterCommit();
}
}
//>>> DDD / Aggregate Root
