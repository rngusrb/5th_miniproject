package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class BestSellerCounted extends AbstractEvent {

    private Long id;

    public BestSellerCounted(Platform aggregate) {
        super(aggregate);
    }

    public BestSellerCounted() {
        super();
    }
}
//>>> DDD / Domain Event
