package server.team_a.todayhouse.src.product.model;

import lombok.*;
import server.team_a.todayhouse.src.image.model.ProductImage;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProductGrid {
    private Long idx;
    private String shopName;
    private String title;
    private ProductImage productImage;
    private Long total;
    private long discountRatio;
    private long reviewCount;
    private float grade;
    private boolean scapped;
}