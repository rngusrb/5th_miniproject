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
    AdminRepository adminRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='RegistAuthorRequested'"
    )
    public void wheneverRegistAuthorRequested_RequestAuthor(
        @Payload RegistAuthorRequested registAuthorRequested
    ) {
        System.out.println(
            "\n\n##### listener RequestAuthor : " +
            registAuthorRequested +
            "\n\n"
        );

         Admin admin = new Admin();
        admin.setId(registAuthorRequested.getId());
        admin.setAuthorLoginId(registAuthorRequested.getAuthorLoginId());
        admin.setAuthorPw(registAuthorRequested.getAuthorPw());
        admin.setAuthorName(registAuthorRequested.getAuthorName());
        admin.setAuthorInfo(registAuthorRequested.getAuthorInfo());
        admin.setAuthorPortfolio(registAuthorRequested.getAuthorPortfolio());
        admin.setIsActive(registAuthorRequested.getIsActive());
        admin.setCreateDate(registAuthorRequested.getCreateDate());

        adminRepository.save(admin);

        

        
    }
}
//>>> Clean Arch / Inbound Adaptor
