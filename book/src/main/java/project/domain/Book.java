package project.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
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

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    private String bookSummary;

    private String bookCoverImage;

    private String bookContent;

    private Integer viewCount;

    private Integer likeCount;

    private Integer price;

    public static BookRepository repository() {
        BookRepository bookRepository = BookApplication.applicationContext.getBean(
            BookRepository.class
        );
        return bookRepository;
    }

    //<<< Clean Arch / Port Method
    public void viewBook() {
        //implement business logic here:

        // 열람 시도

        
        BookViewed bookViewed = new BookViewed(this);
        bookViewed.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method
    
    //<<< Clean Arch / Port Method
    public void addWishlist() {
        //implement business logic here:

        // 관심도서 등록 시도

        
        AddedToWishlist addedToWishlist = new AddedToWishlist(this);
        addedToWishlist.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
