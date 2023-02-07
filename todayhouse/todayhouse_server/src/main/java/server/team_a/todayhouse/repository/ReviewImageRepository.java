package server.team_a.todayhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.team_a.todayhouse.src.image.model.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
