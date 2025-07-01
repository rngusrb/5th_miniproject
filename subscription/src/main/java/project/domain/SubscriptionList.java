package project.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import java.util.Date;


//<<< EDA / CQRS
@Entity
@Table(name = "SUBSCRIPTION_LIST_TABLE")
@Data
public class SubscriptionList {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Long userId;
    private Long bookId;
    private Long authorId;
    private String bookTitle;
    private String bookSummary;
    private String bookCoverImage;
    private String category;
    private Date subscribedDate;
}
