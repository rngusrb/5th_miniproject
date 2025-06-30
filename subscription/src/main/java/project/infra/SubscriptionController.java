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
            String type = (String) payload.get("eventType");

            if (type == null) {
                return "eventType is required in JSON payload";
            }

            ObjectMapper mapper = new ObjectMapper();
            String jsonPayload = mapper.writeValueAsString(payload);

            kafkaTemplate.send("project", jsonPayload);

            return "Event sent: " + type;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to send event: " + e.getMessage();
        }
    }   
   



}
//>>> Clean Arch / Inbound Adaptor
