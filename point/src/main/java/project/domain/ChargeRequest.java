package project.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargeRequest {
    private Long userId;
    private Integer amount;
}
 