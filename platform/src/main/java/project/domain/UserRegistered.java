package project.domain;

import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

@Data
@ToString
public class UserRegistered extends AbstractEvent {

    private Long id;
}
