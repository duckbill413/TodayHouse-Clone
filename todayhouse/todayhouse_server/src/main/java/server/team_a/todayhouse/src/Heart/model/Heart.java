package server.team_a.todayhouse.src.Heart.model;

import lombok.*;
import org.hibernate.annotations.Where;
import server.team_a.todayhouse.src.base.BaseEntity;
import server.team_a.todayhouse.src.place.model.Place;
import server.team_a.todayhouse.src.users.model.Users;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Where(clause = "deleted = false")
@Entity
public class Heart extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private boolean status;
    private boolean deleted;
    @ManyToOne
    @ToString.Exclude
    private Place place;
    @ManyToOne
    @ToString.Exclude
    private Users users;
    private void changeStatus(){
        this.status = this.status ? false : true;
    }
}
