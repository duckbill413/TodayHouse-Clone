package server.team_a.todayhouse.src.users.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostLoginRes {

    private Integer userIdx;
    private String jwt;
}
