package server.team_a.todayhouse.src.users.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetUserRes {
    private int userIdx;
    private String userName;
    private String ID;
    private String email;
    private String password;
}
