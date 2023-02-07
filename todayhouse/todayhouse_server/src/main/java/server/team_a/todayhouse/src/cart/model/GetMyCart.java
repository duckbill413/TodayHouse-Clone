package server.team_a.todayhouse.src.cart.model;

import lombok.*;
import server.team_a.todayhouse.src.base.embedded.Cost;
import server.team_a.todayhouse.src.product.model.GetProductForCartRes;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetMyCart {
    private Cost totalCost;
    private List<GetProductForCartRes> product;

    public void initTotalCost(){
        totalCost = new Cost();
        product.forEach(p -> {
            totalCost.setCost(totalCost.getCost() + p.getCost().getCost() * p.getCount());
            totalCost.setDelivery(totalCost.getDelivery() + p.getCost().getDelivery());
            totalCost.setDiscount(totalCost.getDiscount() + p.getCost().getDiscount() * p.getCount());
            totalCost.setTotal(totalCost.getCost() - totalCost.getDiscount() + totalCost.getDelivery());
        });
    }
}
