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

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    private PointRepository pointRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {
        // Ignore unrelated events
    }

    /**
     * 구독권 없고, 구독 이력도 없는 경우 책 열람 요청 시
     * SubscriptionCheckCase4 이벤트 수신 → 포인트 차감 로직 호출
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionCheckCase4'"
    )
    public void wheneverSubscriptionCheckCase4_PointBalanceChange(
        @Payload SubscriptionCheckCase4 event
    ) {
        System.out.println("##### listener PointBalanceChange : " + event);
        Point.pointBalanceChange(event);
    }


    
}

