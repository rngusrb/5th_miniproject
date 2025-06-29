package project.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "PointUsageHistory_table")
@Data
public class PointUsageHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // 사용자 ID
    private Long userId;

    // 열람한 도서 ID
    private Long bookId;

    // 변경된 포인트 (양수: 적립, 음수: 사용)
    private Integer changePoint;

    // 변경 시각
    private Date changeDate;

    // 변경 후 남은 포인트
    private Integer remainPoint;

}
