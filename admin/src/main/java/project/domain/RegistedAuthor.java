package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class RegistedAuthor extends AbstractEvent {

    private Long id;
    private Long authorId;

    public RegistedAuthor(Admin aggregate) {
        super(aggregate);
    }

    public RegistedAuthor() {
        super();
    }
}
//>>> DDD / Domain Event
