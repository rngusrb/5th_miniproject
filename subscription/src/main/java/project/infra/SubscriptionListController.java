package project.infra;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.domain.*;

@RestController
@RequestMapping("/api/v1/subscription-lists")
public class SubscriptionListController {

    @Autowired
    SubscriptionListRepository subscriptionListRepository;

    // ✅ 사용자별 구독 목록을 그룹화하여 반환
    @GetMapping("/grouped-by-user")
    public List<UserSubscriptionGroup> getGroupedByUser() {
        List<SubscriptionList> all = (List<SubscriptionList>) subscriptionListRepository.findAll();

        // userId 기준으로 그룹핑
        Map<Long, List<SubscriptionList>> grouped = all.stream()
            .collect(Collectors.groupingBy(SubscriptionList::getUserId));

        // 결과 DTO로 변환
        List<UserSubscriptionGroup> result = new ArrayList<>();
        for (Map.Entry<Long, List<SubscriptionList>> entry : grouped.entrySet()) {
            result.add(new UserSubscriptionGroup(entry.getKey(), entry.getValue()));
        }

        return result;
    }

    // ✅ DTO 클래스 내부 정의 또는 별도 파일로 분리 가능
    @Data
    @AllArgsConstructor
    static class UserSubscriptionGroup {
        private Long userId;
        private List<SubscriptionList> subscriptions;
    }
}
