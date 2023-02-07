package server.team_a.todayhouse.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import server.team_a.todayhouse.repository.UsersRepository;
import server.team_a.todayhouse.security.dto.UsersSecurityDTO;
import server.team_a.todayhouse.src.users.model.UserRole;
import server.team_a.todayhouse.src.users.model.Users;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UsersService extends DefaultOAuth2UserService {
    @Value("${secret.key.oauth.password}")
    private String OAUTH_PASSWORD_KEY;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("OAuth Login");

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();
        log.info("OAuth2 Client Name: " + clientName);

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap = oAuth2User.getAttributes();

        switch (clientName){
            case "kakao":
                return generateKakaoDTO(getKakaoEmail(paramMap), paramMap);
            default: return null;
        }
    }
    private UsersSecurityDTO generateKakaoDTO(HashMap<String, String> kakao, Map<String, Object> params){
        String email = kakao.get("email");
        Optional<Users> result = usersRepository.findByEmail(email);
        // DB에 이메일 사용자가 존재 하지 않는 경우
        if (result.isEmpty()){
            Users users = Users.builder()
                    .email(email)
                    .password(passwordEncoder.encode(OAUTH_PASSWORD_KEY))
                    .nickname(kakao.get("nickname"))
                    .profileImg(kakao.get("profile_image"))
                    .thumbnailImage(kakao.get("thumbnail_image"))
                    .phoneNumber(null)
                    .deleted(false)
                    .social(true)
                    .build();
            users.addRole(UserRole.USER);
            Users newUser = usersRepository.save(users);

            // UsersSecurity
            UsersSecurityDTO usersSecurityDTO = new UsersSecurityDTO(
                    newUser.getIdx(), email, newUser.getPassword(), false, true,
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")) // FEAT: 소셜로그인 권한
            );
            usersSecurityDTO.setProps(params);
            return usersSecurityDTO;
        }
        else {
            // 이메일 사용자 기 존재
            Users users = result.get();
            UsersSecurityDTO usersSecurityDTO = new UsersSecurityDTO(
                    users.getIdx(), users.getEmail(), users.getPassword(),
                    users.isDeleted(), users.isSocial(),
                    users.getRoleSet().stream().map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.name()))
                            .collect(Collectors.toList())
            );
            return usersSecurityDTO;
        }
    }

    private HashMap<String, String> getKakaoEmail(Map<String, Object> paramMap){
        HashMap<String, String> kakao = new HashMap<>();
        Object value = paramMap.get("kakao_account");
        LinkedHashMap accountMap = (LinkedHashMap) value;
        LinkedHashMap accountValue = (LinkedHashMap) paramMap.get("properties");
        kakao.put("email", (String) accountMap.get("email"));
        if (accountValue.containsKey("nickname"))
            kakao.put("nickname", (String) accountValue.get("nickname"));
        if (accountValue.containsKey("profile_image"))
            kakao.put("profile_image", (String) accountValue.get("profile_image"));
        if (accountValue.containsKey("thumbnail_image"))
            kakao.put("thumbnail_image", (String) accountValue.get("thumbnail_image"));
        return kakao;
    }
}
