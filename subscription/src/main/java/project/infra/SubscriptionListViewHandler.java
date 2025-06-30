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

    // SubscriptionAdded 이벤트 처리
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='SubscriptionAdded'")
    public void whenSubscriptionAdded_thenCreate(@Payload SubscriptionAdded event) {
        try {
            System.out.println("\n##### [View] SubscriptionAdded Event received: " + event);

            SubscriptionList list = new SubscriptionList();
            list.setUserId(event.getUserId());
            list.setBookId(event.getBookId());

            // 나머지는 BookViewed로 나중에 채워짐
            subscriptionListRepository.save(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // BookViewed 이벤트 처리
    @StreamListener(value = KafkaProcessor.INPUT, condition = "headers['type']=='BookViewed'")
    public void whenBookViewed_thenUpdate(@Payload BookViewed event) {
        try {
            System.out.println("\n##### [View] BookViewed Event received: " + event);

            Optional<SubscriptionList> optional = subscriptionListRepository.findByBookId(event.getBookId());

            optional.ifPresent(list -> {
                list.setBookTitle(event.getBookTitle());
                list.setAuthorId(event.getAuthorId());
                list.setCategory(event.getCategory());
                list.setBookCoverImage(event.getBookCoverImage());
                list.setBookSummary(event.getBookSummary());
                list.setBookContent(event.getBookContent());
                subscriptionListRepository.save(list);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
