package project.domain;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.jpa.repository.JpaRepository;

import project.domain.*;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "manuscripts",
    path = "manuscripts"
)

public interface ManuscriptRepository extends JpaRepository<Manuscript, Long> {
    Optional<Manuscript> findFirstByAuthorIdAndStatus(Long authorId, String status);
    List<Manuscript> findByAuthorId(Long authorId);
    List<Manuscript> findAll();
}
