package project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserDTO {

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long userId;
        private Long userPw;
        private String token;

        // JWT만 포함한 응답을 위한 생성자 (보안용)
        public Response(Long userId, String token) {
            this.userId = userId;
            this.token = token;
        }
    }
}
