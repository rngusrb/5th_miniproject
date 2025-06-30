package project.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    SubscriptionRepository subscriptionRepository;

    @Autowired
    SubscriptionListRepository subscriptionListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {
        // 예비 이벤트 수신 핸들러
    }

    //------------------------------------------------------------------
    // 구독권이 있을 때
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookAccessGranted'"
    )
    public void wheneverBookAccessGranted_SubscriptionSuccess(
        @Payload BookAccessGranted bookAccessGranted
    ) {
        System.out.println(
            "\n\n##### listener SubscriptionSuccess : " +
            bookAccessGranted +
            "\n\n"
        );

        // 도메인 로직 실행
        Subscription.subscriptionSuccess(bookAccessGranted);
    }

    //------------------------------------------------------------------
    // 구독 후 포인트 차감 → 구독 추가
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointMinus'"
    )
    public void wheneverPointMinus_SubscriptionAdd(
        @Payload PointMinus pointMinus
    ) {
        System.out.println(
            "\n\n##### listener SubscriptionAdd : " + pointMinus + "\n\n"
        );

        Subscription.subscriptionAdd(pointMinus);
    }

    //------------------------------------------------------------------
    // SubscriptionAdded 이벤트 수신 → ReadModel 저장
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionAdded'"
    )
    public void wheneverSubscriptionAdded_StoreReadModel(
        @Payload SubscriptionAdded subscriptionAdded
    ) {
        if (subscriptionAdded == null || subscriptionAdded.getUserId() == null)
            return;

        System.out.println(
            "\n\n##### listener ReadModel Save : " + subscriptionAdded + "\n\n"
        );

        SubscriptionList subscriptionList = new SubscriptionList();
        subscriptionList.setUserId(subscriptionAdded.getUserId());
        subscriptionList.setBookId(subscriptionAdded.getBookId());

        // TODO: bookTitle, authorId 등은 필요한 경우 다른 서비스에서 조회

        subscriptionListRepository.save(subscriptionList);
    }
}
//>>> Clean Arch / Inbound Adaptor
