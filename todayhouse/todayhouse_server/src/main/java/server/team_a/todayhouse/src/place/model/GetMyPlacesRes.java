package server.team_a.todayhouse.src.place.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GetMyPlacesRes {

    int placeIdx;

    String space;

    String imageUrl;


}
