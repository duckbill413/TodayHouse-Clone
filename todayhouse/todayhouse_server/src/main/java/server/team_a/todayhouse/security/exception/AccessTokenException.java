package server.team_a.todayhouse.security.exception;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import server.team_a.todayhouse.config.BaseResponse;
import server.team_a.todayhouse.config.BaseResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class AccessTokenException extends RuntimeException{
    BaseResponseStatus baseResponseStatus;
    public AccessTokenException (BaseResponseStatus baseResponseStatus){
        super(baseResponseStatus.getMessage());
        this.baseResponseStatus = baseResponseStatus;
    }

    public void sendResponseError(HttpServletResponse response){
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        BaseResponse baseResponse = new BaseResponse(baseResponseStatus);
        Gson gson = new Gson();
        String responseStr = gson.toJson(baseResponse);

        try{
            response.getWriter().println(responseStr);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
