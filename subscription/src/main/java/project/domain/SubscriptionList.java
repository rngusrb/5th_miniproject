package project.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
// Admin이 구독권/구독 목록 조회할 때 필요한 속성 선언부분 
// --> 이 부분 프론트엔드에서 원하는 것들 선언하면 문제 xx
@Entity
@Table(name = "SubscriptionList_table")
@Data
public class SubscriptionList {

    @Id
    private Long userId;

    // 구독 (책) 목록 조회 
    private Long bookId;

    // 구독권 및 구독 시간 확인
    private LocalDate readDate;

    // 구독권 유형(구독권) 목록 조회
    private Long subscriptionType;


}
