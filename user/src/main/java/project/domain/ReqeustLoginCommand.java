package project.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class ReqeustLoginCommand {
    private Long userId;
    private Long userPw;
}