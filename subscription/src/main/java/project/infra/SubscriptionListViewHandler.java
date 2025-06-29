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
public class SubscriptionListViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private SubscriptionListRepository subscriptionListRepository;
    //>>> DDD / CQRS

    @StreamListener(condition="headers['type']=='SubscriptionList'",
                   value=KafkaProcessor.INPUT)
    public void whenSubscriptionList_thenCreate(@Payload SubscriptionList ev) {
        // 이벤트 안에 엔티티 전체 값이 들어 있다고 가정
        repo.save(ev);
    }
}
