package server.team_a.todayhouse.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
@Setter
public class UsersSecurityDTO extends User implements OAuth2User {
    private Long idx;
    private String email;
    private String password;
    private boolean deleted;
    private boolean social;
    private Map<String, Object> props;

    public UsersSecurityDTO(Long idx, String email, String password,
                            boolean deleted, boolean social,
                            Collection<? extends GrantedAuthority> authorities) {
        super(email, password, authorities);
        this.idx = idx;
        this.email = email;
        this.password = password;
        this.deleted = deleted;
        this.social = social;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.getProps();
    }

    @Override
    public String getName() {
        return this.email;
    }
}
