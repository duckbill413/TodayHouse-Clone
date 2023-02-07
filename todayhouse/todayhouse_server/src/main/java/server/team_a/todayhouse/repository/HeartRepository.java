package server.team_a.todayhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.team_a.todayhouse.src.Heart.model.Heart;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    long countByPlaceIdx(Long placeIdx);
}
