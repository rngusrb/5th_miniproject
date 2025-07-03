package project.domain;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.repository.query.Param;
import project.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel = "points", path = "points")
public interface PointRepository extends PagingAndSortingRepository<Point, Long> {

    // // 최신 포인트 로그 1건 (native)
    // @Query(value = "SELECT * FROM point_table WHERE user_id = :userId ORDER BY change_date DESC LIMIT 1", nativeQuery = true)
    // Point findLatestByUserId(@org.springframework.data.repository.query.Param("userId") Long userId);

        // 최신 포인트 로그 1건 (JPQL 버전)
    @Query("SELECT p FROM Point p WHERE p.userId = :userId ORDER BY p.changeDate DESC")
    Point findTopByUserIdOrderByChangeDateDesc(@Param("userId") Long userId);

}