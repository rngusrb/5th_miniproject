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

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/subscriptions-lists") // api 경로
@Transactional
public class SubscriptionController {

    @Autowired
    private SubscriptionListRepository repo;

    // 전체 구독 목록 조회
    @GetMapping
    public List<SubscriptionList> list() {
        return repo.findAll();
    }


    // /subscription-lists/summaries -> 주소임
    @GetMapping("/summaries")
    public List<SubscriptionSummary> summaries() {
        // 전체 ReadModel 조회 
        List<SubscriptionList> all = repo.findAll();

        //  userId 로 그룹핑 → DTO 로 매핑
        Map<Long, List<SubscriptionList>> byUser =
            all.stream().collect(Collectors.groupingBy(SubscriptionList::getUserId));
        
        return byUser.entrySet().stream()
            .map(e -> {
                Long userId = e.getKey();
                List<SubscriptionList> rows = e.getValue();

                // assume all rows for a user share the same subscriptionType
                Long subscriptionType = rows.get(0).getSubscriptionType();

                List<Long> bookIds = rows.stream()
                    .map(SubscriptionList::getBookId)
                    .collect(Collectors.toList());

                return new SubscriptionSummary(userId, subscriptionType, bookIds);
            })
            .collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}")
    public List<SubscriptionList> byUser(@PathVariable Long userId) {
        return repo.findByUserId(userId);
    }
}