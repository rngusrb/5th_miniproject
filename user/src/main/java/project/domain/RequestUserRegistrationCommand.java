package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RequestUserRegistrationCommand {

    private Long userId;
    private Long userPw;
    
}