package project.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import project.config.kafka.KafkaProcessor;
import project.domain.*;

import java.util.Date;
import java.util.Map;
import org.springframework.web.client.RestTemplate;
import project.SubscriptionApplication;
//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}


    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookAccessGranted'"
    )
    public void wheneverBookAccessGranted_AddSubscription(
        @Payload BookAccessGranted granted
    ) {
        System.out.println("\n\n✅ BookAccessGranted received: " + granted + "\n\n");

        Subscription subscription = new Subscription();
        subscription.setUserId(granted.getUserId());
        subscription.setBookId(granted.getBookId());
        subscriptionRepository.save(subscription);
        
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointMinus'"
    )
    public void wheneverBookAccessGranted_AddSubscription(
        @Payload PointMinus granted
    ) {
        System.out.println("\n\n✅ PointMinus received: " + granted + "\n\n");

        Subscription subscription = new Subscription();
        subscription.setUserId(granted.getUserId());
        subscription.setBookId(granted.getBookId());
        subscriptionRepository.save(subscription);
    }



    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionSaved'"
    )
    @Transactional
    public void wheneverSubscriptionSaved_UpdateReadModel(
        @Payload SubscriptionSaved event
    ) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8088/books/" + event.getBookId();
            Map<String, Object> book = restTemplate.getForObject(url, Map.class);

            System.out.println("\uD83D\uDCDA 받아온 책 정보: " + book);

            if (book == null) return;

            SubscriptionList view = new SubscriptionList();
            view.setUserId(event.getUserId());
            view.setBookId(event.getBookId());
            view.setAuthorId(Long.parseLong(book.get("authorId").toString()));
            view.setBookTitle(book.get("bookTitle").toString());
            view.setBookSummary(book.get("bookSummary").toString());
            view.setBookCoverImage(book.get("bookCoverImage").toString());
            view.setCategory(book.get("category").toString());
            view.setSubscribedDate(new Date());

            SubscriptionListRepository repo = SubscriptionApplication.applicationContext
                .getBean(SubscriptionListRepository.class);
            repo.save(view);

            System.out.println("✅ SubscriptionList 저장 완료");

        } catch (Exception e) {
            System.out.println("❌ ReadModel 저장 실패:");
            e.printStackTrace();
        }
    }


    
}
//>>> Clean Arch / Inbound Adaptor
