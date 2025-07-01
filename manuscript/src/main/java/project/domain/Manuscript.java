package project.domain;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Manuscript_table")
@Data
//<<< DDD / Aggregate Root
public class Manuscript {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long manuscriptId;

    private Long authorId;

    private String title;

    private String category;

    @Column(length = 4000)
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @Column(length = 1000)
    private String summary;

    @Column(length = 1000)
    private String bookCoverImage;

    private String status;

    // 원고 등록 이벤트 실행
    public void register() {
        this.status = "REGISTERED";
        this.createDate = LocalDateTime.now();
        // 이벤트 발행
        ManuscriptSubmitted event = new ManuscriptSubmitted(this);
        event.publishAfterCommit();
    }

    // 원고 편집 이벤트 실행
    public void edit() {
        this.status = "EDITED";
        this.modifyDate = LocalDateTime.now();
        // 이벤트 발행
        ManuscriptEdited event = new ManuscriptEdited(this);
        event.publishAfterCommit();
    }

    // 원고 삭제 이벤트 실행
    public void delete() {
        // 이벤트 발행
        ManuscriptDeleted event = new ManuscriptDeleted(this);
        event.publishAfterCommit();
    }

    // 원고 요약 이벤트 실행
    public void summary() {
        // 이벤트 발행
        ManuscriptSummary event = new ManuscriptSummary(this);
        event.publishAfterCommit();
    }

    // 원고 카테고리 생성 이벤트 실행
    public void createCategory() {
        // 이벤트 발행
        ManuscriptCategoryCreated event = new ManuscriptCategoryCreated(this);
        event.publishAfterCommit();
    }

    // 원고 표지 이미지 생성 이벤트 실행
    public void createImg() {
        CoverImageCreated event = new CoverImageCreated(this);
        event.publishAfterCommit();
    }

    // 출판 승인 요청 이벤트 실행
    public void publish() {
        PublicationRequested event = new PublicationRequested(this);
        event.publishAfterCommit();
    }
}
//>>> DDD / Aggregate Root
