package project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorLoginResponseDTO {
    private Long authorId;
    private String authorLoginId;
    private String token;
}