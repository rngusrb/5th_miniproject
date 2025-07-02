package project.domain;

import lombok.Data;

@Data
public class RequestAuthorRegistrationCommand {
    private String authorLoginId;
    private String authorPw;
}
