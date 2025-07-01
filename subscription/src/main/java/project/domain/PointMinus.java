package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;


@Data
@ToString
public class PointMinus extends AbstractEvent {
    private Long userId;
    private Long bookId;

    public PointMinus(Long userId, Long bookId) {
        super();
        this.userId = userId;
        this.bookId = bookId;
    }

    public PointMinus() {
        super();
    }
}
