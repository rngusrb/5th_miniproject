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

    
}
//>>> Clean Arch / Inbound Adaptor
