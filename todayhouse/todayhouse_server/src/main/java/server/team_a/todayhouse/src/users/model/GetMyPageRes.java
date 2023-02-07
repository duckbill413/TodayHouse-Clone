package server.team_a.todayhouse.src.users.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetMyPageRes {

    public GetMyPageRes(String nickname, String profileImg, String introduce) {
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.introduce = introduce;
    }

    String nickname;

    String profileImg;

    String introduce;

    int scrapCount;

    int heartCount;

    int reviewCount;

}
