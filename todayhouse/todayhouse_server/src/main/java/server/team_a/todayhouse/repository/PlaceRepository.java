package server.team_a.todayhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.team_a.todayhouse.src.place.model.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
