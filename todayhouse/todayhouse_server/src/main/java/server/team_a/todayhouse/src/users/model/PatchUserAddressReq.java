package server.team_a.todayhouse.src.users.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PatchUserAddressReq {
    @NotBlank(message = "주소를 확인 해주세요.")
    @Size(max = 150, message = "주소의 길이는 최대 150자 입니다.")
    private String address;
    @NotBlank(message = "우편번호를 확인 해주세요.")
    @Size(max = 10, message = "우편번호 10자 이하 입니다.")
    private String zipcode;
}
