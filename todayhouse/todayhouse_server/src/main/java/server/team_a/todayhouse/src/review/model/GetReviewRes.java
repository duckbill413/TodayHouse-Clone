package server.team_a.todayhouse.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import server.team_a.todayhouse.src.image.model.Image;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetReviewRes {
    private Long idx;
    private String title;
    private String body;
    private float grade;
    private boolean deleted;
    private List<Image> reviewImages;
}
