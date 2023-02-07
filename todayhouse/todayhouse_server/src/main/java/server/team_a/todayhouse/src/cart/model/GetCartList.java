package server.team_a.todayhouse.src.cart.model;

import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import server.team_a.todayhouse.src.base.embedded.Cost;
import server.team_a.todayhouse.src.product.model.GetProductShort;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCartList {
    private Long idx;
    private Cost cost;
    private Long count;
    private Long amount;
    private boolean deleted;
    private boolean ordered;
    private GetProductShort product;
}
