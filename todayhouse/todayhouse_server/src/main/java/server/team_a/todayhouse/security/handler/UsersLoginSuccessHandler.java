package server.team_a.todayhouse.security.handler;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import server.team_a.todayhouse.config.BaseResponse;
import server.team_a.todayhouse.security.dto.UsersSecurityDTO;
import server.team_a.todayhouse.src.users.model.GetLoginFilterRes;
import server.team_a.todayhouse.src.users.model.PostLoginRes;
import server.team_a.todayhouse.src.users.model.UserRole;
import server.team_a.todayhouse.utils.JWTUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
public class UsersLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        UsersSecurityDTO usersSecurityDTO = (UsersSecurityDTO) authentication.getPrincipal();

        Map<String, Object> claim = Map.of("userIdx", usersSecurityDTO.getIdx());
        String accessToken = jwtUtil.generateJwtToken(claim, 10); // FIXME: refresh token 추가

        List<UserRole> roles = new ArrayList<>();
        usersSecurityDTO.getAuthorities().stream().collect(Collectors.toList()).forEach(grantedAuthority -> {
            UserRole userRole = Enum.valueOf(UserRole.class, grantedAuthority.getAuthority().substring(5));
            roles.add(userRole);
        });
        GetLoginFilterRes getLoginFilterRes = new GetLoginFilterRes(usersSecurityDTO.getIdx(), accessToken, roles);
        BaseResponse<GetLoginFilterRes> baseResponse = new BaseResponse<>(getLoginFilterRes);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(baseResponse);
        response.getWriter().println(jsonStr);
//        Map<String, String> keyMap = Map.of(
//                "X-ACCESS-TOKEN", accessToken
//        );
//        String jsonStr = gson.toJson(keyMap);
    }
}
