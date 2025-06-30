package project.domain;

import java.time.LocalDateTime;
import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

@Data
@ToString
public class PublicationRequested extends AbstractEvent {

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

