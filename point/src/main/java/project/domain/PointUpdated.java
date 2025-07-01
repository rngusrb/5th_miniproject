package project.domain;


import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class PointUpdated extends AbstractEvent {

    private Long userId;

    private Integer changePoint;

    private Long pointSum;
    
    private String reason;

    private Date changeDate;

    public PointUpdated(Point aggregate) {
        super(aggregate);

        this.userId = aggregate.getUserId();
        this.changePoint = aggregate.getChangePoint();
        this.pointSum = aggregate.getPointSum();
        this.reason = aggregate.getReason();
        this.changeDate = aggregate.getChangeDate();       
    }
    
    public PointUpdated() {
        super();
    }
}
//>>> DDD / Domain Event
