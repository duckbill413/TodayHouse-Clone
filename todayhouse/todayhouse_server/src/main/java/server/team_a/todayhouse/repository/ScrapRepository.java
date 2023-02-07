package server.team_a.todayhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.team_a.todayhouse.src.scrap.model.Scrap;

import java.util.List;
import java.util.Optional;

public interface ScrapRepository extends JpaRepository<Scrap, Long> {
    boolean existsScrapByUsersIdxAndProductIdx(Long userIdx, Long productIdx);
    Optional<Scrap> findByProductIdxAndUsersIdx(Long productIdx, Long usersIdx);
    Optional<Scrap> findByPlaceIdxAndUsersIdx(Long placeIdx, Long usersIdx);
    long countByPlaceIdx(Long placeIdx);
    long countByProductIdx(Long productIdx);
}
