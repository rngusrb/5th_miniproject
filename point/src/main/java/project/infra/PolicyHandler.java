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
        condition = "headers['type']=='BookAccessDenied'"
    )
    public void wheneverBookAccessDenied_PointBalanceChange(
        @Payload BookAccessDenied bookAccessDenied
    ) {
        BookAccessDenied event = bookAccessDenied;
        System.out.println(
            "\n\n##### listener PointBalanceChange : " +
            bookAccessDenied +
            "\n\n"
        );

        // Sample Logic //
        Point.pointBalanceChange(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='UserRegistered'"
    )
    public void wheneverUserRegistered_PointBalanceChange(
        @Payload UserRegistered userRegistered
    ) {
        UserRegistered event = userRegistered;
        System.out.println(
            "\n\n##### listener PointBalanceChange : " + userRegistered + "\n\n"
        );

        // Sample Logic //
        Point.pointBalanceChange(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
