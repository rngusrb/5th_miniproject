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


    // @StreamListener(
    //     value = KafkaProcessor.INPUT,
    //     condition = "headers['type']=='BookAccessGranted'"
    // )
    // public void wheneverBookAccessGranted_AddSubscription(
    //     @Payload BookAccessGranted granted
    // ) {
    //     System.out.println("\n\n✅ BookAccessGranted received: " + granted + "\n\n");

    //     Subscription subscription = new Subscription();
    //     subscription.setUserId(granted.getUserId());
    //     subscription.setBookId(granted.getBookId());
    //     subscriptionRepository.save(subscription);
        
    // }
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookAccessGranted'"
    )
    public void wheneverBookAccessGranted_AddSubscription(@Payload BookAccessGranted granted) {
        System.out.println("\n\n✅ BookAccessGranted received: " + granted + "\n\n");
        Subscription.addSubscription(granted.getUserId(), granted.getBookId());
    }

    // @StreamListener(
    //     value = KafkaProcessor.INPUT,
    //     condition = "headers['type']=='PointMinus'"
    // )
    // public void wheneverBookAccessGranted_AddSubscription(
    //     @Payload PointMinus granted
    // ) {
    //     System.out.println("\n\n✅ PointMinus received: " + granted + "\n\n");

    //     Subscription subscription = new Subscription();
    //     subscription.setUserId(granted.getUserId());
    //     subscription.setBookId(granted.getBookId());
    //     subscriptionRepository.save(subscription);
    // }
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointMinus'"
    )
    public void wheneverPointMinus_AddSubscription(@Payload PointMinus granted) {
        System.out.println("\n\n✅ PointMinus received: " + granted + "\n\n");
        Subscription.addSubscription(granted.getUserId(), granted.getBookId());
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
            System.out.println("📬 SubscriptionSaved 수신: " + event);

            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8083/books/" + event.getBookId();
            System.out.println("🌐 Book API 호출 주소: " + url);

            Map<String, Object> book = restTemplate.getForObject(url, Map.class);
            System.out.println("📚 받아온 책 정보: " + book);

            if (book == null) {
                System.out.println("❌ 책 정보가 null입니다.");
                return;
            }

            SubscriptionList view = new SubscriptionList();
            view.setUserId(event.getUserId());
            view.setBookId(event.getBookId());

            // 안전한 값 설정: 널 체크 및 기본값 처리
            Object authorId = book.get("authorId");
            Object bookTitle = book.get("bookTitle");
            Object bookSummary = book.get("bookSummary");
            Object bookCoverImage = book.get("bookCoverImage");
            Object category = book.get("category");

            view.setAuthorId(authorId != null ? Long.parseLong(authorId.toString()) : null);
            view.setBookTitle(bookTitle != null ? bookTitle.toString() : "제목없음");
            view.setBookSummary(bookSummary != null ? bookSummary.toString() : "");
            view.setBookCoverImage(bookCoverImage != null ? bookCoverImage.toString() : "");
            view.setCategory(category != null ? category.toString() : "기타");
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
