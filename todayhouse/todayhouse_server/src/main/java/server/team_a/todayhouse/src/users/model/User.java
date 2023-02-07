package server.team_a.todayhouse.src.users.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class User {

    private long idx;
    private String email;

    private String password;

    private String nickname;

    private String address;

    private String phoneNumber;

    private String profileImg;

    private String introduce;
    private boolean deleted;
    private boolean social;
}
