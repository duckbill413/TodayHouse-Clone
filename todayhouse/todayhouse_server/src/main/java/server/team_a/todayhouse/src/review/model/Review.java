package server.team_a.todayhouse.src.review.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Where;
import server.team_a.todayhouse.src.image.model.ReviewImage;
import server.team_a.todayhouse.src.product.model.Product;
import server.team_a.todayhouse.src.users.model.Users;
import server.team_a.todayhouse.src.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Where(clause = "deleted = false")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(length = 300, nullable = false)
    private String title;
    @Column(length = 1000)
    private String body;
    private float grade;
    private boolean deleted;
    @OneToMany(mappedBy = "review")
    @Builder.Default
    @JsonManagedReference
    private List<ReviewImage> reviewImages = new ArrayList<>();
    @ManyToOne
    @ToString.Exclude
    private Product product;
    @ManyToOne
    @ToString.Exclude
    private Users users;
}
