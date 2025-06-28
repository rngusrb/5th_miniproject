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
    private SubscriptionRepository subscriptionRepository;

    // BookViewed → subscriptionCheck 호출
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookViewed'"
    )
    public void wheneverBookViewed_SubscriptionCheck(
        @Payload BookViewed bookViewed
    ) {
        System.out.println("##### listener SubscriptionCheck : " + bookViewed);
        Subscription.subscriptionCheck(bookViewed);
    }

    // PointUpdated → subscriptionAdd 호출
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointUpdated'"
    )
    public void wheneverPointUpdated_SubscriptionAdd(
        @Payload PointUpdated pointUpdated
    ) {
        System.out.println("##### listener SubscriptionAdd : " + pointUpdated);
        Subscription.subscriptionAdd(pointUpdated);
    }

    //  subscriptionCheck 에서 발행된 4가지 케이스 이벤트 처리(case1~4)
    

    // 구독권 o , 구독권 x
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionCheckCase1'"
    )
    public void whenCase1_thenHandle(@Payload SubscriptionCheckCase1 ev) {
        System.out.println("##### listener Case1 (Voucher O, Sub X): " + ev);
        // TODO: 케이스1 처리 로직 추가 (예: PointBalanceService.grantNewSubscriptionPoints(...))
    }

    // 구독권 o , 구독권 o
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionCheckCase2'"
    )
    public void whenCase2_thenHandle(@Payload SubscriptionCheckCase2 ev) {
        System.out.println("##### listener Case2 (Voucher X, Sub O): " + ev);
        // TODO: 케이스2 처리 로직 추가 (예: PointBalanceService.refundSubscriptionPoints(...))
    }

    // 구독권 x , 구독권 o
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionCheckCase3'"
    )
    public void whenCase3_thenHandle(@Payload SubscriptionCheckCase3 ev) {
        System.out.println("##### listener Case3 (Voucher O, Sub O): " + ev);
        // TODO: 케이스3 처리 로직 추가 (예: PointBalanceService.extendSubscription(...))
    }

    // 구독권 x , 구독권 x
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionCheckCase4'"
    )
    public void whenCase4_thenHandle(@Payload SubscriptionCheckCase4 ev) {
        System.out.println("##### listener Case4 (Voucher X, Sub X): " + ev);
        // TODO: 케이스4는 특별 처리 필요 없으면 로그만 남기기
    }
}
