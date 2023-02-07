package server.team_a.todayhouse.src.place.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import server.team_a.todayhouse.src.image.model.PlaceImage;
import server.team_a.todayhouse.src.scrap.model.Scrap;
import server.team_a.todayhouse.src.base.BaseEntity;
import server.team_a.todayhouse.src.users.model.Users;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Place extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @OneToMany(mappedBy = "place")
    @Builder.Default
    private List<PlaceImage> placeImages = new ArrayList<>();
    @OneToMany(mappedBy = "place")
    @Builder.Default
    @ToString.Exclude
    private List<Scrap> scraps = new ArrayList<>();
    @ManyToOne
    @ToString.Exclude
    @JsonBackReference
    private Users users;
}
