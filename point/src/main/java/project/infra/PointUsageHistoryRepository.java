package project.infra;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import project.domain.*;

@RepositoryRestResource(
    collectionResourceRel = "pointUsageHistories",
    path = "pointUsageHistories"
)
public interface PointUsageHistoryRepository
    extends PagingAndSortingRepository<PointUsageHistory, Long> {}
