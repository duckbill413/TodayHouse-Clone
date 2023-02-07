package server.team_a.todayhouse.src.order.model;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;
@Getter
@Setter
public class OrderSelectedReq {
    @NotNull(message = "주문할 항목이 없습니다.")
    private List<Long> cartList;
}
