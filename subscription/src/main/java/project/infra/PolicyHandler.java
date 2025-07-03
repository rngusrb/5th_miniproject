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

import java.time.LocalDateTime;
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

            // book 데이터에서 필드 추출
            view.setAuthorId(book.get("authorId") != null ? Long.parseLong(book.get("authorId").toString()) : null);
            view.setBookTitle(book.get("bookTitle") != null ? book.get("bookTitle").toString() : "제목없음");
            view.setCategory(book.get("category") != null ? book.get("category").toString() : "기타");

            view.setBookSummary(book.get("bookSummary") != null ? book.get("bookSummary").toString() : "");
            view.setBookCoverImage(book.get("bookCoverImage") != null ? book.get("bookCoverImage").toString() : "");
            view.setBookContent(book.get("bookContent") != null ? book.get("bookContent").toString() : "");

            view.setViewCount(book.get("viewCount") != null ? Integer.parseInt(book.get("viewCount").toString()) : 0);
            view.setLikeCount(book.get("likeCount") != null ? Integer.parseInt(book.get("likeCount").toString()) : 0);
            view.setPrice(book.get("price") != null ? Integer.parseInt(book.get("price").toString()) : 0);

            // 시간 설정
            LocalDateTime now = LocalDateTime.now();
            view.setCreateDate(now);
            view.setModifyDate(now);

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
