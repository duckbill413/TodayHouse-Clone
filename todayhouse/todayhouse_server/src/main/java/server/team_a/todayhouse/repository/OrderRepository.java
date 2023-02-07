package server.team_a.todayhouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.team_a.todayhouse.src.order.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<List<Order>> findByUsersIdx(Long usersIdx);
    Optional<Order> findByIdxAndUsersIdx(Long orderIdx, Long usersIdx);
}
