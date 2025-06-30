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

    
    // SubscriptionSaved 이벤트 수신 시 실행
    // 사용자의 구독 정보(bookId, userId)를 기반으로 subscriptionList 에 새로운 행을 추가한다.
    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscriptionSaved_then_CREATE(
        @Payload SubscriptionSaved subscriptionSaved
    ) {
        if (!subscriptionSaved.validate()) return;

        SubscriptionList subscriptionList = new SubscriptionList();
        subscriptionList.setUserId(subscriptionSaved.getUserId());
        subscriptionList.setBookId(subscriptionSaved.getBookId());
        subscriptionList.setSubscribedDate(new Date()); // 또는 subscriptionSaved.getTimestamp()

        // 기본 정보만 들어감 (Book 정보는 아래에서 보강 필요)
        subscriptionListRepository.save(subscriptionList);
    }

    // BookViewed 이벤트 수신 시, 해당 도서 정보를 업데이트
    // 해당 bookId를 가진 모든 구독 레코드를 찾아서 채워준다.
    @StreamListener(KafkaProcessor.INPUT)
    public void whenBookViewed_then_UPDATE(
        @Payload BookViewed bookViewed
    ) {
        if (!bookViewed.validate()) return;

        // 이미 등록된 SubscriptionList에서 bookId 일치하는 항목들 가져옴
        List<SubscriptionList> list = subscriptionListRepository.findByBookId(bookViewed.getBookId());

        for (SubscriptionList item : list) {
            item.setBookTitle(bookViewed.getBookTitle());
            item.setBookSummary(bookViewed.getBookSummary());
            item.setBookCoverImage(bookViewed.getBookCoverImage());
            item.setCategory(bookViewed.getCategory());
            subscriptionListRepository.save(item);
        }
    }
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
