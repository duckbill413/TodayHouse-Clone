package server.team_a.todayhouse.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import server.team_a.todayhouse.repository.UsersRepository;
import server.team_a.todayhouse.security.dto.UsersSecurityDTO;
import server.team_a.todayhouse.src.users.model.Users;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUsersDetailService implements UserDetailsService {
    private final UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Users> result = usersRepository.getWithRoles(email);
        if (result.isEmpty()){
            throw new UsernameNotFoundException("해당하는 이메일의 사용자가 없습니다.");
        }
        Users users = result.get();
        UsersSecurityDTO usersSecurityDTO = new UsersSecurityDTO(
                users.getIdx(), users.getEmail(), users.getPassword(),
                users.isDeleted(), users.isSocial(),
                users.getRoleSet().stream().map(userRole -> new SimpleGrantedAuthority(
                        "ROLE_" + userRole.name()
                )).collect(Collectors.toList())
        );
        log.info("log-in user : "+ usersSecurityDTO);

        return usersSecurityDTO;
    }
    public UserDetails loadUserByUserIdx(Long userIdx) throws UsernameNotFoundException {
        Optional<Users> result = usersRepository.findByIdx(userIdx);
        if (result.isEmpty()){
            throw new UsernameNotFoundException("해당하는 PK의 사용자가 없습니다.");
        }
        Users users = result.get();
        UsersSecurityDTO usersSecurityDTO = new UsersSecurityDTO(
                users.getIdx(), users.getEmail(), users.getPassword(),
                users.isDeleted(), users.isSocial(),
                users.getRoleSet().stream().map(userRole -> new SimpleGrantedAuthority(
                        "ROLE_" + userRole.name()
                )).collect(Collectors.toList())
        );
        log.info("log-in user : "+ usersSecurityDTO);

        return usersSecurityDTO;
    }
}
