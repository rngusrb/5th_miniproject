package project.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import project.config.kafka.KafkaProcessor;
import project.domain.*;

@Service
public class ManuscriptListViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private ManuscriptListRepository manuscriptListRepository;
    //>>> DDD / CQRS
}
