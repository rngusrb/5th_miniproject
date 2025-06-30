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

    @PostPersist
    public void onPostPersist() {
        RegistedAuthor registedAuthor = new RegistedAuthor(this);
        registedAuthor.publishAfterCommit();
    }

    public static AdminRepository repository() {
        AdminRepository adminRepository = AdminApplication.applicationContext.getBean(
            AdminRepository.class
        );
        return adminRepository;
    }

    //<<< Clean Arch / Port Method
    public static void registerAuthor(RegistAuthorRequested registAuthorRequested) {
    Admin admin = new Admin();
    admin.setAuthorId(registAuthorRequested.getAuthorId()); // 여기가 문제 없는지
    repository().save(admin);
}
    //>>> Clean Arch / Port Method
}