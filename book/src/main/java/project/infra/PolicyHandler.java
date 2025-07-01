package project.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

import javax.naming.NameParser;
import javax.transaction.Transactional;

import org.apache.tomcat.jni.Local;
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
    BookRepository bookRepository;

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

        Book book = new Book();
        
        book.setAuthorId(event.getAuthorId());
        book.setBookContent(event.getContent());
        book.setBookCoverImage(event.getBookCoverImage());
        book.setBookSummary(event.getSummary());
        book.setBookTitle(event.getTitle());
        book.setCategory(event.getCategory());

        bookRepository.save(book);

        // Sample Logic //

        // PublishBookCommand command = new PublishBookCommand();
        // Book.publishBook(command);
    }
}
//>>> Clean Arch / Inbound Adaptor
