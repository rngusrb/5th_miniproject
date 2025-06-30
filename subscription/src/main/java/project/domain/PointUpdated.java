package project.domain;

import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;


@ToString
@Data
public class PointUpdated extends AbstractEvent {

    private Long userId;
    private Long bookId;

    public PointUpdated() {
        super();
    }

    public PointUpdated(Point aggregate) {
        super(aggregate);
    }
}
