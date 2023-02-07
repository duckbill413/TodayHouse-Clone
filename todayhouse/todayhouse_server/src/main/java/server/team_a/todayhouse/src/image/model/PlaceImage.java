package server.team_a.todayhouse.src.image.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;
import server.team_a.todayhouse.src.place.model.Place;

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
public class PlaceImage extends Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(length = 60)
    @Enumerated(EnumType.STRING)
    private Space space;
    @Column(columnDefinition = "TEXT")
    private String message;
    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Place place;
}
