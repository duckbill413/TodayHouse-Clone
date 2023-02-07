package server.team_a.todayhouse.src.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostProductImageReq {
    @NotNull(message = "이미지 순서를 입력해주세요.")
    private Long sequence;
    @NotNull(message = "이미지 파일이 잘못 되었습니다.")
    private String imageUrl;
}
