package project.infra;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import java.util.Optional;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/api/v1/subscriptions")
@Transactional
public class SubscriptionController {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/publish")
    public String publishEvent(@RequestBody Map<String, Object> payload) {
        try {
            String type = (String) payload.get("eventType");  // 이벤트 타입 추출

            if (type == null) {
                return " eventType is required in JSON payload";
            }

            // JSON 직렬화
            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(payload);

            // Kafka 메시지 생성 및 전송
            Message<String> message = MessageBuilder
                .withPayload(jsonPayload)
                .setHeader("type", type)
                .build();

            kafkaTemplate.send("project", message);  // "project"는 Kafka 토픽

            return "Event sent: " + type;

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send event: " + e.getMessage();
        }
    }



}
//>>> Clean Arch / Inbound Adaptor
