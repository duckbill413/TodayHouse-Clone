package server.team_a.todayhouse.src.review.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class GetReviewsRes {

    String nickName;

    String profileImage;

    int productIdx;

    int reviewIdx;

    String date;

    String title;

    String body;

    float grade;

    List<String> images;
}
