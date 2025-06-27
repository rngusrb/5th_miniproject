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
        condition = "headers['type']=='BookViewed'"
    )
    public void wheneverBookViewed_SubscriptionCheck(
        @Payload BookViewed bookViewed
    ) {
        BookViewed event = bookViewed;
        System.out.println(
            "\n\n##### listener SubscriptionCheck : " + bookViewed + "\n\n"
        );

        // Sample Logic //
        Subscription.subscriptionCheck(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointUpdated'"
    )
    public void wheneverPointUpdated_SubscriptionAdd(
        @Payload PointUpdated pointUpdated
    ) {
        PointUpdated event = pointUpdated;
        System.out.println(
            "\n\n##### listener SubscriptionAdd : " + pointUpdated + "\n\n"
        );

        // Sample Logic //
        Subscription.subscriptionAdd(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
