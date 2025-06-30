package project.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class BookDTO {

    @Setter @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Post {
        private Long bookId;
        private Long authorId;
        private String bookTitle;
        private String category;
        private String bookSummary;
        private String bookCoverImage;
        private String bookContent;
    }

    @Setter @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Put{
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
    }

    @Setter @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response{
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
    }

    @Setter @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Patch{
        private Long bookId;
        private String bookTitle;
        private String category;
        private String bookSummary;
        private String bookCoverImage;
        private String bookContent;
    }
    
    @Setter @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ViewBookRequest{
        private Long bookId;
    }
    
    @Setter @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FavoriteBookRequest{
        private Long bookId;
    }

    public static BookDTO.Response toResponse(Book book) {

        return new BookDTO.Response(
            book.getBookId(),
            book.getAuthorId(),
            book.getBookTitle(),
            book.getCategory(),
            book.getCreateDate(),
            book.getModifyDate(),
            book.getBookSummary(),
            book.getBookCoverImage(),
            book.getBookContent(),
            book.getViewCount(),
            book.getLikeCount(),
            book.getPrice()
        );
    }
}
