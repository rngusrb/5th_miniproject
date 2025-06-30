package project.domain;

import java.util.*;
import lombok.*;
import project.domain.*;
import project.infra.AbstractEvent;

@Data
@ToString
public class BookAccessGranted extends AbstractEvent {

    private Long id;
}

// test