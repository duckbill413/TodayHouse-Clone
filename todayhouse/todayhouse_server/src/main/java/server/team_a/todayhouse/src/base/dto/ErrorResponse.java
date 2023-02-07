package server.team_a.todayhouse.src.base.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import server.team_a.todayhouse.config.BaseResponseStatus;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ErrorResponse {
    String statusCode;
    String requestUrl;
    int code;
    String message;
    String resultCode;
    List<Error> errorList;

    public ErrorResponse(BaseResponseStatus status){
        this.statusCode = status.toString();
        this.code = status.getCode();
        this.message = status.getMessage();
        this.resultCode = status.isSuccess() ? "SUCCESS" : "FAIL";
    }
}
