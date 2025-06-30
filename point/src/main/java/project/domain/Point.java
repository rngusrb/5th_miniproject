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
import project.domain.PointMinus;

@Entity
@Table(name = "Point_table")
@Data
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private Date changeDate;

    private Integer changePoint;

    private Long pointSum;

    private String reason;

    public static PointRepository repository() {
        return PointApplication.applicationContext.getBean(PointRepository.class);
    }

    // 포인트 차감: 도서 접근 거부 시
    public static void pointBalanceChange(BookAccessDenied bookAccessDenied) {
        Point point = new Point();
        point.setUserId(bookAccessDenied.getUserId());
        point.setChangeDate(new Date());
        point.setChangePoint(-bookAccessDenied.getRequiredPoint());

        // 기존 포인트 조회 후 누적 계산
        Long currentSum = repository().findById(bookAccessDenied.getUserId())
            .map(Point::getPointSum)
            .orElse(0L);

        point.setPointSum(currentSum - bookAccessDenied.getRequiredPoint());
        point.setReason(bookAccessDenied.getReason() != null ? bookAccessDenied.getReason() : "Access Denied");

        repository().save(point);

        PointMinus pointMinus = new PointMinus(point);
        pointMinus.publishAfterCommit();
    }

    // 포인트 지급: 신규가입
    public static void pointBalanceChange(UserRegistered userRegistered) {
        Point point = new Point();
        point.setUserId(userRegistered.getUserId());
        point.setChangeDate(new Date());

        // 일반 신규회원은 1000포인트 지급!
        int grantPoint = 1000;
        // kt 직원이면 5000 포인트 지급 !
        if ("kt".equalsIgnoreCase(userRegistered.getUserType())) {
            grantPoint = 5000;
        }

        point.setChangePoint(grantPoint);

        // 기존 포인트 누적
        Long currentSum = repository().findById(userRegistered.getUserId())
            .map(Point::getPointSum)
            .orElse(0L);

        point.setPointSum(currentSum + grantPoint);
        point.setReason("Welcome Bonus");

        repository().save(point);

        PointMinus pointMinus = new PointMinus(point);
        pointMinus.publishAfterCommit();
    }
}