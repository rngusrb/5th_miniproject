package project.domain;

import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

@Data
@ToString
public class UserRegistered extends AbstractEvent {


    // 유저 아이디
    private Long userId;
    // 가입 유형
    //private String userType;

    public UserRegistered() {
         super();
     }

     public UserRegistered(Long id) {
         super();

         this.userId = id;
         //this.userType= userType;
     }
}
