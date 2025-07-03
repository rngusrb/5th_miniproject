package project.domain;

import java.util.Date;

public class PointResponse {
    private Long userId;
    private Date changeDate;
    private Integer changePoint;
    private Long pointSum;
    private String reason;

    public PointResponse(Long userId, Date changeDate, Integer changePoint, Long pointSum, String reason) {
        this.userId = userId;
        this.changeDate = changeDate;
        this.changePoint = changePoint;
        this.pointSum = pointSum;
        this.reason = reason;
    }

    // Getter 없어도 Jackson이 직렬화 못할 수 있으니 아래 추가
    public Long getUserId() { return userId; }
    public Date getChangeDate() { return changeDate; }
    public Integer getChangePoint() { return changePoint; }
    public Long getPointSum() { return pointSum; }
    public String getReason() { return reason; }
}
