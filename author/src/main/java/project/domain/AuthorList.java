package project.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "AuthorList_table")
@Data
public class AuthorList {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String authorName;
    private Date createDate;
    private String authorInfo;
    private String authorPortfolio;
    private Boolean isActive;
    private String authorPw;
}
