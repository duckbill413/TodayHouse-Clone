package server.team_a.todayhouse.src.base.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Error {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String field;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String invalidValue;
}
