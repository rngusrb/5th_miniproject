package project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;
import project.BookApplication;
import project.domain.AddedToWishlist;
import project.domain.BookViewed;
import project.domain.EditedBookInfo;
import project.domain.GetLikeCount;
import project.domain.GetViewCount;

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

    private Date createDate;

    private Date modifyDate;

    private String bookSummary;

    private String bookCoverImage;

    private String bookContent;

    @PostPersist
    public void onPostPersist() {
        BookViewed bookViewed = new BookViewed(this);
        bookViewed.publishAfterCommit();

        GetViewCount getViewCount = new GetViewCount(this);
        getViewCount.publishAfterCommit();

        GetLikeCount getLikeCount = new GetLikeCount(this);
        getLikeCount.publishAfterCommit();

        AddedToWishlist addedToWishlist = new AddedToWishlist(this);
        addedToWishlist.publishAfterCommit();

        EditedBookInfo editedBookInfo = new EditedBookInfo(this);
        editedBookInfo.publishAfterCommit();
    }

    public static BookRepository repository() {
        BookRepository bookRepository = BookApplication.applicationContext.getBean(
            BookRepository.class
        );
        return bookRepository;
    }

    //<<< Clean Arch / Port Method
    public void viewBook() {
        //implement business logic here:

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
