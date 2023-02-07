package server.team_a.todayhouse.src.product.model;

import lombok.*;
import server.team_a.todayhouse.src.image.model.ProductImage;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProductShort {
    private Long idx;
    private String shopName;
    private String title;
}