package server.team_a.todayhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.team_a.todayhouse.src.image.model.PlaceImage;

public interface PlaceImageRepository extends JpaRepository<PlaceImage, Long> {
}
