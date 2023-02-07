package server.team_a.todayhouse.src.product.model;

import lombok.*;
import server.team_a.todayhouse.src.base.validation.EnumValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostProductReq {
    @NotBlank(message = "제목을 입력하지 않았습니다.")
    @Size(max = 500, message = "제목은 500자 이하로 입력 가능합니다.")
    private String title;
    @NotBlank(message = "내용을 입력하지 않았습니다.")
    private String body;
    @NotNull(message = "금액을 입력해주세요.")
    private Long cost;
    private Long discount = 0L;
    private Long delivery = 2500L;
    @EnumValid(enumClass = Category.class, ignoreCase = true, message = "카테고리에 없는 값 입니다.")
    private String category;
    private List<PostProductImageReq> images;
}
