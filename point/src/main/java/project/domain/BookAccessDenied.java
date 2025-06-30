package project.domain;

import java.time.LocalDateTime;
import java.util.*;

import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;


@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
public class BookAccessDenied extends AbstractEvent {

    private Long userId;               // 유저 ID
    private Long bookId;          // 접근 거절된 도서 ID
    private Integer requiredPoint; // 부족했던 포인트 수량
    private String reason;        // 거절 사유 (예: "포인트 부족")
      
    // 이벤트 발생 시간

    // public BookAccessDenied() {
    //     super();
    //     this.timestamp = LocalDateTime.now();
    // }

    // public BookAccessDenied(Long id, Long bookId, Integer requiredPoint, String reason) {
    //     this.userId = id;
    //     this.bookId = bookId;
    //     this.requiredPoint = requiredPoint;
    //     this.reason = reason;
    //     this.timestamp = LocalDateTime.now();
    // }


}
