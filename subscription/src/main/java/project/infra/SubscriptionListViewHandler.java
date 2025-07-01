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

    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscriptionAdded_then_CREATE_1(
        @Payload SubscriptionAdded subscriptionAdded
    ) {
        try {
            if (!subscriptionAdded.validate()) return;

            // view 객체 생성
            SubscriptionList subscriptionList = new SubscriptionList();
            // view 객체에 이벤트의 Value 를 set 함
            subscriptionList.setUserId(subscriptionAdded.getUserId());
            subscriptionList.setBookId(subscriptionAdded.getBookId());
            // view 레파지 토리에 save
            subscriptionListRepository.save(subscriptionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookViewed_then_UPDATE_1(@Payload BookViewed bookViewed) {
        try {
            if (!bookViewed.validate()) return;
            // view 객체 조회

            List<SubscriptionList> subscriptionListList = subscriptionListRepository.findByBookId(
                bookViewed.getBookId()
            );
            for (SubscriptionList subscriptionList : subscriptionListList) {
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                subscriptionList.setBookTitle(bookViewed.getBookTitle());
                subscriptionList.setAuthorId(
                    String.valueOf(bookViewed.getAuthorId())
                );
                subscriptionList.setCategory(bookViewed.getCategory());
                subscriptionList.setBookCoverImage(
                    bookViewed.getBookCoverImage()
                );
                subscriptionList.setBookSummary(bookViewed.getBookSummary());
                subscriptionList.setBookContent(bookViewed.getBookContent());
                // view 레파지 토리에 save
                subscriptionListRepository.save(subscriptionList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //>>> DDD / CQRS
}
