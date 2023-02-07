package server.team_a.todayhouse.src.place.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GetPlacesRes {

    public GetPlacesRes(int placeIdx, String thumbnail, String message) {
        this.placeIdx = placeIdx;
        this.message = message;
        this.thumbnail = thumbnail;
        this.isScraped = false;
    }

    int placeIdx;

    String message;

    String thumbnail;





    boolean isScraped;
}
