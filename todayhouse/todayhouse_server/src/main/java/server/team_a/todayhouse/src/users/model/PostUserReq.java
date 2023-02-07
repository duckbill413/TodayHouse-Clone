package server.team_a.todayhouse.src.users.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserReq {
    private String email;
    private String password;
    private String nickname;
}
