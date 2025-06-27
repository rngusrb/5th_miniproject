package project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import project.AdminApplication;
import project.domain.FinishedPublish;
import project.domain.RegistedAuthor;

@Entity
@Table(name = "Admin_table")
@Data
//<<< DDD / Aggregate Root
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long authorId;

    private Long bookId;

    @PostPersist
    public void onPostPersist() {
        RegistedAuthor registedAuthor = new RegistedAuthor(this);
        registedAuthor.publishAfterCommit();

        FinishedPublish finishedPublish = new FinishedPublish(this);
        finishedPublish.publishAfterCommit();
    }

    public static AdminRepository repository() {
        AdminRepository adminRepository = AdminApplication.applicationContext.getBean(
            AdminRepository.class
        );
        return adminRepository;
    }

    //<<< Clean Arch / Port Method
    public void registerBook() {
        //implement business logic here:

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
