package project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

public class ManuscriptDTO {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long manuscriptId;

        private Long authorId;

        private String title;

        private String category;

        private String content;

        private LocalDateTime createDate;

        private LocalDateTime modifyDate;

        private String summary;

        private String bookCoverImage;

        private String status;

    }

    public static ManuscriptDTO.Response toResponse(Manuscript manuscript) {
        return new ManuscriptDTO.Response(
                manuscript.getManuscriptId(),
                manuscript.getAuthorId(),
                manuscript.getTitle(),
                manuscript.getCategory(),
                manuscript.getContent(),
                manuscript.getCreateDate(),
                manuscript.getModifyDate(),
                manuscript.getSummary(),
                manuscript.getBookCoverImage(),
                manuscript.getStatus()
        );
    }
}