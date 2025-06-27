package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class CategoryEdited extends AbstractEvent {

    private Long id;

    public CategoryEdited(Platform aggregate) {
        super(aggregate);
    }

    public CategoryEdited() {
        super();
    }
}
//>>> DDD / Domain Event
