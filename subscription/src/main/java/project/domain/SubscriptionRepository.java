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

    //  사용자 ID로 구독 목록 조회
    List<Subscription> findByUserId(Long userId);

    // 사용자와 도서 ID로 구독 여부 확인 (중복 구독 방지 등)
    boolean existsByUserIdAndBookId(Long userId, Long bookId);

    
}