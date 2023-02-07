package server.team_a.todayhouse.src.order.model;

import server.team_a.todayhouse.src.base.embedded.Address;
import server.team_a.todayhouse.src.base.embedded.Cost;
import server.team_a.todayhouse.src.cart.model.GetCartList;

import java.util.List;

import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderedRes {
    private Long idx;
    private Cost totalCost;
    private Address address;
    private boolean ordered;
    private boolean determine;
    private String shipping;
    private boolean deleted;
    private List<GetCartList> carts;
}
