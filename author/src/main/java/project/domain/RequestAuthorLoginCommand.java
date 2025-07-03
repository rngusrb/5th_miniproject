// RequestAuthorLoginCommand.java
package project.domain;

import lombok.Data;

@Data
public class RequestAuthorLoginCommand {
    private String authorLoginId;
    private String authorPw;
}

