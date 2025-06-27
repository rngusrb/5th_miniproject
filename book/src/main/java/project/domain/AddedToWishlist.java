package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class AddedToWishlist extends AbstractEvent {

    private Long id;

    public AddedToWishlist(Book aggregate) {
        super(aggregate);
    }

    public AddedToWishlist() {
        super();
    }
}
//>>> DDD / Domain Event
