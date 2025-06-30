package project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import project.PointApplication;
import project.domain.PointMinus;

@Entity
@Table(name = "Point_table")
@Data
//<<< DDD / Aggregate Root
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private Date changeDate;

    private Integer changePoint;

    private Long pointSum;

    private String reason;

    public static PointRepository repository() {
        PointRepository pointRepository = PointApplication.applicationContext.getBean(
            PointRepository.class
        );
        return pointRepository;
    }

    //<<< Clean Arch / Port Method
    public static void pointBalanceChange(NoUsage noUsage) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        PointMinus pointMinus = new PointMinus(point);
        pointMinus.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(noUsage.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);

            PointMinus pointMinus = new PointMinus(point);
            pointMinus.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void pointBalanceChange(UserRegistered userRegistered) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        PointMinus pointMinus = new PointMinus(point);
        pointMinus.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(userRegistered.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);

            PointMinus pointMinus = new PointMinus(point);
            pointMinus.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
