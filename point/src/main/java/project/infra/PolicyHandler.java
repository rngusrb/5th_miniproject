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
    PointRepository pointRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SubscriptionNotOwned'"
    )
    public void wheneverSubscriptionNotOwned_PointBalanceChange(
        @Payload SubscriptionNotOwned subscriptionNotOwned
    ) {
        SubscriptionNotOwned event = subscriptionNotOwned;
        System.out.println(
            "\n\n##### listener PointBalanceChange : " +
            subscriptionNotOwned +
            "\n\n"
        );

        // Sample Logic //
        Point.pointBalanceChange(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
