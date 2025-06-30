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

        // 실제 작가 등록 처리 로직
        RegisterAuthorCommand command = new RegisterAuthorCommand();
        // 필요한 필드들을 command에 채워 넣을 수 있음
        // 예: command.setAuthorName(registAuthorRequested.getAuthorName());

        
    }
}
//>>> Clean Arch / Inbound Adaptor
