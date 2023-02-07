package server.team_a.todayhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.team_a.todayhouse.src.cart.model.Cart;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<List<Cart>> findByUsersIdxAndOrdered(Long usersIdx, boolean ordered);
    Optional<Cart> findByIdxAndUsersIdx(Long cartIdx, Long usersIdx);
}
