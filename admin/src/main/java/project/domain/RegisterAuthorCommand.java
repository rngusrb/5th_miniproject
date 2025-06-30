package project.domain;

import lombok.Data;

@Data
public class RegisterAuthorCommand {
    private String authorName;
    private String authorInfo;
    private String authorPortfolio;
    private Boolean isActive;
}