package project.domain;

import java.util.Date;

import lombok.Data;

@Data
public class RegisterAuthorCommand {
    private Long id;
    private Long authorId;
    private String authorPw;
    private String authorName;
    private Date createDate;
    private String authorInfo;
    private String authorPortfolio;
    private Boolean isActive;
}