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
@RequestMapping(value="/points")
@Transactional
public class PointController {

    @Autowired
    PointRepository pointRepository;


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
}
//>>> Clean Arch / Inbound Adaptor
