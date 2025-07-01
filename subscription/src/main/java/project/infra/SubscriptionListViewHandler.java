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
import java.util.Date;


@Service
public class SubscriptionListViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private SubscriptionListRepository subscriptionListRepository;


    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookViewed_then_CREATE_1(@Payload BookViewed event) {
        if (!event.validate()) return;

        SubscriptionList view = new SubscriptionList();
        view.setUserId(event.getUserId()); // 또는 UserId가 따로 있으면 그것 사용
        view.setBookId(event.getBookId());
        view.setAuthorId(event.getAuthorId());
        view.setBookTitle(event.getBookTitle());
        view.setBookSummary(event.getBookSummary());
        view.setBookCoverImage(event.getBookCoverImage());
        view.setCategory(event.getCategory());
        view.setSubscribedDate(new Date());

        subscriptionListRepository.save(view);
    }

    //>>> DDD / CQRS
}
