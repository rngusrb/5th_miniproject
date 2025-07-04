package project.infra;
import java.util.Date;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import project.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value = "/points")
@Transactional
public class PointController {

    @Autowired
    PointRepository pointRepository;

    // ✅ 포인트 충전 API
    @PutMapping("/{userId}/pluspoints")
    public Point addPoints(
        @PathVariable Long userId,
        @RequestBody PointRequest request
    ) {
        Integer points = request.getPoints();

        Long currentSum = 0L;
        Point latest = pointRepository.findLatestByUserId(userId);
        if (latest != null) {
            currentSum = latest.getPointSum();
        }

        Point point = new Point();
        point.setUserId(userId);
        point.setChangeDate(new Date());
        point.setChangePoint(points);
        point.setPointSum(currentSum + points);
        point.setReason("수동 충전");

        return pointRepository.save(point);
    }

    // ✅ 포인트 조회 API (프론트에서 사용하는 부분)
    @GetMapping("/{userId}")
    public Point getLatestPoint(@PathVariable Long userId) {
        Point latest = pointRepository.findLatestByUserId(userId);
        if (latest == null) {
            Point empty = new Point();
            empty.setUserId(userId);
            empty.setChangeDate(new Date());
            empty.setChangePoint(0);
            empty.setPointSum(0L);
            empty.setReason("초기 생성");
            return empty;
        }
        return latest;
    }
    // ✅ 포인트 충전 API (프론트 연동용)
    @PostMapping("/charge")
    public Point chargePoints(@RequestBody ChargeRequest request) {
        Long userId = request.getUserId();
        Integer amount = request.getAmount();

        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }

        Long currentSum = 0L;
        Point latest = pointRepository.findLatestByUserId(userId);
        if (latest != null) {
            currentSum = latest.getPointSum();
        }

        Point point = new Point();
        point.setUserId(userId);
        point.setChangeDate(new Date());
        point.setChangePoint(amount);
        point.setPointSum(currentSum + amount);
        point.setReason("사용자 충전");

        return pointRepository.save(point);
    }
}
