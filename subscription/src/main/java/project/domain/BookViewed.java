package project.domain;

import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

@Data
@ToString
public class BookViewed extends AbstractEvent {
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
    private Long bookId;
    private Long authorId;
    private String bookTitle;
    private String category;
    private Date createDate;
    private Date modifyDate;
    private String bookSummary;
    private String bookCoverImage;
    private String bookContent;
}
