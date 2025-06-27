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
    AdminRepository adminRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PublicationRequested'"
    )
    public void wheneverPublicationRequested_RequestPublishBook(
        @Payload PublicationRequested publicationRequested
    ) {
        PublicationRequested event = publicationRequested;
        System.out.println(
            "\n\n##### listener RequestPublishBook : " +
            publicationRequested +
            "\n\n"
        );

        // Sample Logic //

        RegisterBookCommand command = new RegisterBookCommand();
        Admin.registerBook(command);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RegistAuthorRequested'"
    )
    public void wheneverRegistAuthorRequested_RequestAuthor(
        @Payload RegistAuthorRequested registAuthorRequested
    ) {
        RegistAuthorRequested event = registAuthorRequested;
        System.out.println(
            "\n\n##### listener RequestAuthor : " +
            registAuthorRequested +
            "\n\n"
        );

        // Sample Logic //

        RegisterAuthorCommand command = new RegisterAuthorCommand();
        Admin.registerAuthor(command);
    }
}
//>>> Clean Arch / Inbound Adaptor
