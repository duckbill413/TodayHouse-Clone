package server.team_a.todayhouse.src.place.model;



import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
@JsonAutoDetect
@NoArgsConstructor
public class PatchPlaceReq {

    String message;
}
