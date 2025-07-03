package project.domain;

import java.util.Date;

import lombok.Data;

@Data
public class RegistAuthorRequested {
    private Long id;
    private String authorLoginId;
    private String authorPw;
    private String authorName;
    private Date createDate;
    private String authorInfo;
    private String authorPortfolio;
    private Boolean isActive;
}