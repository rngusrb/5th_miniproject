package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PointMinus extends AbstractEvent {

    private Long userId;

    private Integer changePoint;

    private Long pointSum;
    
    private String reason;

    private Date changeDate;

    public PointMinus(Point aggregate) {
        super(aggregate);

        this.userId = aggregate.getUserId();
        this.changePoint = aggregate.getChangePoint();
        this.pointSum = aggregate.getPointSum();
        this.reason = aggregate.getReason();
        this.changeDate = aggregate.getChangeDate();       
    }

    public PointMinus() {
        super();
    }
}
//>>> DDD / Domain Event
