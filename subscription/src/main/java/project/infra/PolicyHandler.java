package project.infra;

import java.util.Map;
import java.util.HashMap;
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
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}


    //
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookAccessGranted'"
    )
    public void wheneverBookAccessGranted_SubscriptionSuccess(@Payload String eventString) {
    try {
        ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        
        // 1. BookAccessGranted 정보 추출
        BookAccessGranted event = mapper.readValue(eventString, BookAccessGranted.class);

        // 2. bookId는 별도로 추출
        Map<String, Object> raw = mapper.readValue(eventString, Map.class);
        Long bookId = raw.get("bookId") != null ? Long.valueOf(raw.get("bookId").toString()) : null;

        System.out.println("bookAccessGranted: " + event);
        System.out.println("bookId: " + bookId);

        if (bookId != null) {
            Subscription.subscriptionSuccess(event, bookId);
        } else {
            System.out.println("bookId가 포함되지 않음 → 구독 처리 생략");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


    //
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointUpdated'"
    )
    public void wheneverPointUpdated_SubscriptionAdd(@Payload String eventString) {
        try {
            ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            // 1. 도메인 객체 매핑
            PointUpdated event = mapper.readValue(eventString, PointUpdated.class);

            // 2. bookId 별도로 파싱
            Map<String, Object> raw = mapper.readValue(eventString, Map.class);
            Long bookId = raw.get("bookId") != null ? Long.valueOf(raw.get("bookId").toString()) : null;

            System.out.println("PointUpdated 이벤트 수신: " + event);
            System.out.println("bookId: " + bookId);

            if (bookId != null) {
                Subscription.subscriptionAdd(event, bookId);
            } else {
                System.out.println("bookId 누락 → 구독 추가 생략");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
//>>> Clean Arch / Inbound Adaptor
