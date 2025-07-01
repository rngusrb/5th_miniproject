package project.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.domain.*;
import java.util.List;

//<<< Clean Arch / Inbound Adaptor


@RestController
@RequestMapping(value="/subscriptions")
@Transactional
public class SubscriptionController {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    private final SubscriptionListRepository subscriptionListRepository;


    @Autowired
    public SubscriptionController(SubscriptionListRepository subscriptionListRepository) {
        this.subscriptionListRepository = subscriptionListRepository;
    }

    @GetMapping("/list")
    public List<SubscriptionList> getAllSubscriptions() {
        return subscriptionListRepository.findAll();
    }

    @GetMapping("/list/{userId}")
    public List<SubscriptionList> getSubscriptionsByUser(@PathVariable Long userId) {
        return subscriptionListRepository.findByUserId(userId);
    }
}
//>>> Clean Arch / Inbound Adaptor
