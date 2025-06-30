package project.domain;

import lombok.Data;

@Data
public class RegistAuthorRequested {
    private String authorName;
    private String authorInfo;
    private String authorPortfolio;
    private Boolean isActive;
    private Long authorId;
    private String authorPw;
}