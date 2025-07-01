package project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import project.BookApplication;
import project.domain.BookDeleted;
import project.domain.BookPublished;
import project.domain.BookViewed;
import project.domain.EditedBookInfo;

@Entity
@Table(name = "Book_table")
@Data
//<<< DDD / Aggregate Root
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @PrePersist
    public void onPrePersist() {
        this.createDate = LocalDateTime.now();
        this.viewCount = 0;
        this.likeCount = 0;
        this.price = 1000;
    }

    @PostPersist
    public void onPostPersist() {
        // BookViewed bookViewed = new BookViewed(this);
        // bookViewed.publishAfterCommit();

        // EditedBookInfo editedBookInfo = new EditedBookInfo(this);
        // editedBookInfo.publishAfterCommit();

        // BookPublished bookPublished = new BookPublished(this);
        // bookPublished.publishAfterCommit();

        // BookDeleted bookDeleted = new BookDeleted(this);
        // bookDeleted.publishAfterCommit();
    }

    @PreUpdate
    public void onPostUpdate() {
        this.modifyDate = LocalDateTime.now();
    }

    public static BookRepository repository() {
        BookRepository bookRepository = BookApplication.applicationContext.getBean(
            BookRepository.class
        );
        return bookRepository;
    }

    public void ViewBook() {
        //
    }

    public void PublishBook() {
        
        BookPublished bookPublished = new BookPublished(this);
        bookPublished.publishAfterCommit();
    }
}
//>>> DDD / Aggregate Root
