package server.team_a.todayhouse.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import server.team_a.todayhouse.src.users.model.UserRole;
import server.team_a.todayhouse.src.users.model.Users;

import java.util.stream.IntStream;

@SpringBootTest
class UsersRepositoryTest {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    void initialUsers(){
        IntStream.rangeClosed(1, 30).forEach(i -> {
            Users users = Users.builder()
                    .email("th" + i + "@gmail.com")
                    .password(passwordEncoder.encode("th"+i))
                    .nickname("todayhouse" + i)
                    .build();
            users.addRole(UserRole.USER);
            if (i>=10)
                users.addRole(UserRole.SELLER);
            if (i >= 20)
                users.addRole(UserRole.ADMIN);
            usersRepository.save(users);
        });
    }
}