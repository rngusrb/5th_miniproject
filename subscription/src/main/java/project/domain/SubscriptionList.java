package project.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long bookId;

    private Long authorId;

    private String bookTitle;

    private String category;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @Column(length = 1000)
    private String bookSummary;
    
    @Column(length = 1000)
    private String bookCoverImage;

    @Column(length = 4000)        
    private String bookContent;

    private Integer viewCount;

    private Integer likeCount;

    private Integer price;
}

