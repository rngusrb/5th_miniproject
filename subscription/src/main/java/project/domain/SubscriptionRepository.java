package project.domain;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import project.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "subscriptions",
    path = "subscriptions"
)
public interface SubscriptionRepository
    extends PagingAndSortingRepository<Subscription, Long> {

        // 전체 목록

        List<SubscriptionList> findAll();
        // 사용자별 조회

        List<SubscriptionList> findByUserId(Long userId);
        
        // 책별 조회
        List<SubscriptionList> findByBookId(Long bookId);
    }
