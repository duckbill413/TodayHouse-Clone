package server.team_a.todayhouse.src.product.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.team_a.todayhouse.src.base.embedded.Cost;
import server.team_a.todayhouse.src.image.model.Image;

@Getter
@Setter
@NoArgsConstructor
public class GetProductForCartRes {
    private Long cartIdx;
    private Long productIdx;
    private String title;
    private String shopName;
    private Image productImage;
    private Cost cost;
    private Long count;
    private Long amount;

    public void setAmount(){
        if (this.cost != null & this.count != null){
            this.amount = (this.cost.getCost() - this.cost.getDiscount()) * this.count + this.cost.getDelivery();
        }
    }
}
