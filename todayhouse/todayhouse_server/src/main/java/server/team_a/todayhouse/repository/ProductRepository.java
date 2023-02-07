package server.team_a.todayhouse.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import server.team_a.todayhouse.src.product.model.Category;
import server.team_a.todayhouse.src.product.model.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByIdxAndUsersIdx(Long postIdx, Long userIdx);
    Optional<Page<Product>> findByCategory(Category category, Pageable pageable);
    Optional<Page<Product>> findAllByOrderByUpdatedAtDesc(Pageable pageable);
}
