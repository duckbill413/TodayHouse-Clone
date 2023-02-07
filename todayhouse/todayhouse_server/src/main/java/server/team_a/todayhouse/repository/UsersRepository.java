package server.team_a.todayhouse.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.team_a.todayhouse.src.users.model.Users;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    @EntityGraph(attributePaths = "roleSet")
    @Query("select u from Users u where u.email = :email and u.social = false ")
    Optional<Users> getWithRoles(@Param("email") String email);
    @EntityGraph(attributePaths = "roleSet")
    Optional<Users> findByIdx(@Param("idx")Long userIdx);
    @EntityGraph(attributePaths = "roleSet")
    Optional<Users> findByEmail(@Param("email") String email);
    @Modifying
    @Transactional
    @Query("update Users u set u.password = :password where u.email = :email")
    void updatePassword(@Param("password") String password, @Param("email") String email);
}
