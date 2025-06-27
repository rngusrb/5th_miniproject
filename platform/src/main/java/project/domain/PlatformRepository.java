package project.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import project.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "platforms", path = "platforms")
public interface PlatformRepository
    extends PagingAndSortingRepository<Platform, Long> {}
