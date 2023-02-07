package server.team_a.todayhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.team_a.todayhouse.src.image.model.ProductImage;

import java.util.List;
import java.util.Optional;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Optional<List<ProductImage>> findByProductIdx(Long productIdx);
}
