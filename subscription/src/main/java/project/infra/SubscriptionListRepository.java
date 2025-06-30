package project.infra;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import project.domain.*;

@RepositoryRestResource(
    collectionResourceRel = "subscriptionLists",
    path = "subscriptionLists"
)
public interface SubscriptionListRepository
    extends PagingAndSortingRepository<SubscriptionList, Long> {
        List<SubscriptionList> findByUserId(Long userId);
        List<SubscriptionList> findByBookId(Long bookId);
        List<SubscriptionList> findAll();

    }
