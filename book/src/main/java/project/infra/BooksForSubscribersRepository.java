package project.infra;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import project.domain.*;

@RepositoryRestResource(
    collectionResourceRel = "booksForSubscribers",
    path = "booksForSubscribers"
)
public interface BooksForSubscribersRepository
    extends PagingAndSortingRepository<BooksForSubscribers, Long> {}
