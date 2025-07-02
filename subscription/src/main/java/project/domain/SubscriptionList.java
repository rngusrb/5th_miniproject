package project.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "SubscriptionList_table")
@Data
public class SubscriptionList {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;

    private Long bookId;
    private String bookTitle;
    private Long authorId;
    private String category;

    @Column(length = 1000)
    private String bookCoverImage;

    @Column(length = 1000)
    private String bookSummary;

    @Column(length = 4000)        
    private String bookContent;

    private Date subscribedDate;
}
