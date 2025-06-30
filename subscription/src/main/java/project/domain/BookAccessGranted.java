package project.domain;

import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookAccessGranted extends AbstractEvent{
    // private Long userId;
    // private Long userPw;
    private Long userId;
    private Long bookId;

    // public BookAccessGranted(Long userId, Long bookId) {
    //     super();
    //     this.userId = userId;
    //     this.bookId = bookId;
    // }

    // public BookAccessGranted() {
    //     super();
    // }

}
