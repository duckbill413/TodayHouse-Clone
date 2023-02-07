package server.team_a.todayhouse.src.users.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostUserRes {

    private int userIdx;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String jwt;
    private List<UserRole> userRole;
}
