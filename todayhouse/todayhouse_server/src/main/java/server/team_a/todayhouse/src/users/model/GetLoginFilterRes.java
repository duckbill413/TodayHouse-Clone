package server.team_a.todayhouse.src.users.model;

import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetLoginFilterRes {
    private Long userIdx;
    private String jwt;
    private List<UserRole> roles;
}
