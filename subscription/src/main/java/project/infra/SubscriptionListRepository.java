package project.infra;

import java.util.List;
//import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import project.domain.*;

@RepositoryRestResource(
    collectionResourceRel = "subscriptionLists",
    path = "subscriptionLists"
)
public interface SubscriptionListRepository
    extends JpaRepository<SubscriptionList, Long> { 
        List<SubscriptionList> findByUserId(Long userId);
}