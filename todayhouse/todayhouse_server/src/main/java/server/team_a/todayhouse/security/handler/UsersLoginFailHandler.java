package server.team_a.todayhouse.security.handler;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.CharacterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import server.team_a.todayhouse.config.BaseResponse;
import server.team_a.todayhouse.config.BaseResponseStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class UsersLoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        BaseResponse baseResponse = new BaseResponse(BaseResponseStatus.FAILED_TO_LOGIN);
        Gson gson = new Gson();
        String responseStr = gson.toJson(baseResponse);

        try {
            response.getWriter().println(responseStr);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
