package server.team_a.todayhouse.src.image.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.Where;
import server.team_a.todayhouse.src.review.model.Review;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Where(clause = "deleted = false")
public class ReviewImage extends Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @ManyToOne
    @ToString.Exclude
    @JsonBackReference
    private Review review;
}
