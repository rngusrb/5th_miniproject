package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class AdditionalPointPurchased extends AbstractEvent {

    private Long id;

    public AdditionalPointPurchased(Platform aggregate) {
        super(aggregate);
    }

    public AdditionalPointPurchased() {
        super();
    }
}
//>>> DDD / Domain Event
