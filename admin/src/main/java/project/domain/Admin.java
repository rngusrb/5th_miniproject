package project.domain;

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
    private String authorPw;
    private String authorName;
    private String authorInfo;
    private String authorPortfolio;
    private Boolean isActive;

    @PostPersist
    public void onPostPersist() {
        RegistedAuthor registedAuthor = new RegistedAuthor(this);
        registedAuthor.publishAfterCommit();
    }

    public static AdminRepository repository() {
        return AdminApplication.applicationContext.getBean(AdminRepository.class);
    }

    //<<< Clean Arch / Port Method
    public static void registerAuthor(RegistAuthorRequested registAuthorRequested) {
        Admin admin = new Admin();
        admin.setAuthorId(registAuthorRequested.getAuthorId());
        admin.setAuthorPw(registAuthorRequested.getAuthorPw());
        admin.setAuthorName(registAuthorRequested.getAuthorName());
        admin.setAuthorInfo(registAuthorRequested.getAuthorInfo());
        admin.setAuthorPortfolio(registAuthorRequested.getAuthorPortfolio());
        admin.setIsActive(registAuthorRequested.getIsActive());
        repository().save(admin);
    }
    //>>> Clean Arch / Port Method
}