package project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import project.PlatformApplication;
import project.domain.AdditionalPointPurchased;
import project.domain.BestSellerCounted;
import project.domain.CategoryEdited;
import project.domain.NewPointAwarded;

@Entity
@Table(name = "Platform_table")
@Data
//<<< DDD / Aggregate Root
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long authorId;

    @ElementCollection
    private List<String> bestseller;

    private String category;

    private Long point;

    public static PlatformRepository repository() {
        PlatformRepository platformRepository = PlatformApplication.applicationContext.getBean(
            PlatformRepository.class
        );
        return platformRepository;
    }

    //<<< Clean Arch / Port Method
    public static void newPointAward(UserRegistered userRegistered) {
        //implement business logic here:

        /** Example 1:  new item 
        Platform platform = new Platform();
        repository().save(platform);

        NewPointAwarded newPointAwarded = new NewPointAwarded(platform);
        newPointAwarded.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(userRegistered.get???()).ifPresent(platform->{
            
            platform // do something
            repository().save(platform);

            NewPointAwarded newPointAwarded = new NewPointAwarded(platform);
            newPointAwarded.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
