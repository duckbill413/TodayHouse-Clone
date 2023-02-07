package server.team_a.todayhouse.src.review.model;

import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewGrade {
    private Double sum;
    private Long count;
}
