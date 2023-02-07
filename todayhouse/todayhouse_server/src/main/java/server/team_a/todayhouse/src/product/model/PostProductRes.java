package server.team_a.todayhouse.src.product.model;

import lombok.*;
import server.team_a.todayhouse.src.base.embedded.Cost;
import server.team_a.todayhouse.src.image.model.ProductImage;
import server.team_a.todayhouse.src.review.model.Review;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostProductRes {
    private Long idx;
    private String shopName;
    private String title;
    private String body;
    private Cost cost;
    private String category;
    private double grade;
    private boolean deleted;
    private List<ProductImage> images;
    private long scrapCount;
    private long reviewCount;
}
