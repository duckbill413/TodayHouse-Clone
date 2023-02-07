package server.team_a.todayhouse.src.product.model;

import lombok.*;
import server.team_a.todayhouse.src.base.embedded.Cost;
import server.team_a.todayhouse.src.image.model.Image;
import server.team_a.todayhouse.src.image.model.ProductImage;
import server.team_a.todayhouse.src.review.model.GetReviewRes;
import server.team_a.todayhouse.src.review.model.Review;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductInfo {
    private Long idx;
    private String shopName;
    private String title;
    private String body;
    private Cost cost;
    private Integer discountRatio;
    private float grade;
    private String category;
    private boolean deleted;
    private long scrapCount;
    private long reviewCount;
    private List<Image> productImages;
    private List<GetReviewRes> reviews;
//    private List<ProductImage> productImages;
//    private List<Review> reviews;
}
