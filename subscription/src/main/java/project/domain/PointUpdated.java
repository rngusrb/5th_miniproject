package project.domain;

import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

@Data
@ToString
public class PointUpdated extends AbstractEvent {

    private Long userId;
    private Integer point;
    private Date changeDate;
    private Date expireDate;
    private Integer changePoint;
    private Integer remainPoint;
}
