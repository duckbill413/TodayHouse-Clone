package server.team_a.todayhouse.src.scrap.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.Where;
import server.team_a.todayhouse.src.base.BaseEntity;
import server.team_a.todayhouse.src.place.model.Place;
import server.team_a.todayhouse.src.product.model.Product;
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
public class Scrap extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private boolean status;
    private boolean deleted;
    @ManyToOne
    private Place place;
    @ManyToOne
    @JsonBackReference
    private Product product;
    @ManyToOne
    @JsonBackReference
    @ToString.Exclude
    private Users users;
    public void changeStatus(){
        this.status = this.status ? false : true;
    }
    public void setPlace(Place place){
        this.place = place;
        place.getScraps().add(this);
    }

    public void setProduct(Product product){
        this.product = product;
        product.getScraps().add(this);
    }

    public void setUsers(Users users){
        this.users = users;
        users.getScraps().add(this);
    }
}
