package server.team_a.todayhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.team_a.todayhouse.src.product.model.Product;
import server.team_a.todayhouse.src.review.model.Review;
import server.team_a.todayhouse.src.review.model.ReviewGrade;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    long countByProductIdx(Long productIdx);
    @Query(value = "select new server.team_a.todayhouse.src.review.model.ReviewGrade(sum(r.grade), count(r.idx))" +
    "from Review r where r.product = :product")
    ReviewGrade getReviewsSumAndCount(Product product);
}
