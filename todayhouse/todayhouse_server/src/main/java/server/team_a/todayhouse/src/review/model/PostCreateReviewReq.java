package server.team_a.todayhouse.src.review.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PostCreateReviewReq {

    Float grade;

    String body;
}
