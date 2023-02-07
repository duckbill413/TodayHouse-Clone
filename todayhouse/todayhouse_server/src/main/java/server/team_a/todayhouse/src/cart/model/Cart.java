package server.team_a.todayhouse.src.cart.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Where;
import server.team_a.todayhouse.src.base.embedded.Cost;
import server.team_a.todayhouse.src.product.model.Product;
import server.team_a.todayhouse.src.base.BaseEntity;
import server.team_a.todayhouse.src.users.model.Users;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Where(clause = "deleted = false")
public class Cart extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Embedded
    private Cost cost;
    private Long count;
    private Long amount;
    private boolean deleted;
    private boolean ordered;
    @ManyToOne
    @ToString.Exclude
    @JsonBackReference
    private Users users;
    @ManyToOne
    @JsonManagedReference
    private Product product;

    public void setUsers(Users users){
        this.users = users;
        users.getCarts().add(this);
    }
    public void setProduct(Product product){
        this.product = product;
        product.getCarts().add(this);
    }
}
