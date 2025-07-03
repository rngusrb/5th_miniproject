package project.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.domain.*;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/{userId}/{bookId}")
    public String checkSubscription(
        @PathVariable Long userId,
        @PathVariable Long bookId
    ) {
        List<Subscription> list = subscriptionRepository.findByUserId(userId);
        boolean matched = list.stream().anyMatch(sub -> sub.getBookId().equals(bookId));

        System.out.println("✅ 직접 검사 userId=" + userId + ", bookId=" + bookId + ", matched=" + matched);
        return matched ? "true" : "false";
    }



}
//>>> Clean Arch / Inbound Adaptor
