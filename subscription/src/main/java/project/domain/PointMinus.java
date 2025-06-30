package project.domain;

import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;


@ToString
@Data
public class PointMinus extends AbstractEvent {

    private Long userId;
    private Long bookId;

    public PointMinus() {
        super();
    }

    public PointMinus(Point aggregate) {
        super(aggregate);
    }
}
