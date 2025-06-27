package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class EditedBookInfo extends AbstractEvent {

    private Long id;

    public EditedBookInfo(Book aggregate) {
        super(aggregate);
    }

    public EditedBookInfo() {
        super();
    }
}
//>>> DDD / Domain Event
