package server.team_a.todayhouse.src.place.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetPlaceDetailRes {

    public GetPlaceDetailRes(String nickname, String profileImg, String date, String placeImg, String message) {
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.date = date;
        this.placeImg = placeImg;
        this.message = message;
        this.isHearted = false;
        this.isScraped = false;
    }

    String nickname;

    String profileImg;


    String date;

    String placeImg;

    String message;

    int scrapCount;

    int heartCount;

    boolean isScraped;

    boolean isHearted;


}
