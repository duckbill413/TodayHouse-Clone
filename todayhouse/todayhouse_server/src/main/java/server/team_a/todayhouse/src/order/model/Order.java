package server.team_a.todayhouse.src.order.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.Where;
import server.team_a.todayhouse.src.base.BaseEntity;
import server.team_a.todayhouse.src.base.embedded.Address;
import server.team_a.todayhouse.src.base.embedded.Cost;
import server.team_a.todayhouse.src.cart.model.Cart;
import server.team_a.todayhouse.src.product.model.Product;
import server.team_a.todayhouse.src.users.model.Users;

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
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Embedded
    private Cost totalCost;
    @Embedded
    private Address address;
    private boolean ordered = true;
    private boolean determine;
    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    private Shipping shipping = Shipping.NOT_START;
    private boolean deleted;
    @ManyToOne
    @ToString.Exclude
    @JsonBackReference
    private Users users;
    @OneToMany
    @JoinColumn(name = "orders_idx")
    private List<Cart> carts = new ArrayList<>();

    public void initTotalCost(){
        long cost = 0;
        long delivery = 0;
        long discount = 0;

        for (Cart cart : this.carts) {
            cost += cart.getCost().getCost() * cart.getCount();
            delivery += cart.getCost().getDelivery();
            discount += cart.getCost().getDiscount() * cart.getCount();
        }
        this.totalCost = new Cost(cost, delivery, discount);
    }
}
