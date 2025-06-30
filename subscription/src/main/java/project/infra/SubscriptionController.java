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
import java.util.List;

//<<< Clean Arch / Inbound Adaptor

@RestController
<<<<<<< Updated upstream
@RequestMapping(value="/api/v1/subscriptions")
=======
@RequestMapping(value="/subscriptions")
>>>>>>> Stashed changes
@Transactional
public class SubscriptionController {

    @Autowired
    SubscriptionRepository subscriptionRepository;

<<<<<<< Updated upstream
    
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
   


=======
    @Autowired
    SubscriptionListRepository subscriptionListRepository;

    @GetMapping
    public List<SubscriptionList> getAllSubscriptions() {
        return subscriptionListRepository.findAll();
    }


    @GetMapping("/{userId}")
    public List<SubscriptionList> getSubscriptionsByUserId(@PathVariable Long userId) {
        return subscriptionListRepository.findByUserId(userId);
    }

>>>>>>> Stashed changes

}
//>>> Clean Arch / Inbound Adaptor
