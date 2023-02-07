package server.team_a.todayhouse.src.product.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Where;
import server.team_a.todayhouse.src.base.embedded.Cost;
import server.team_a.todayhouse.src.image.model.ProductImage;
import server.team_a.todayhouse.src.order.model.Order;
import server.team_a.todayhouse.src.review.model.Review;
import server.team_a.todayhouse.src.scrap.model.Scrap;
import server.team_a.todayhouse.src.base.BaseEntity;
import server.team_a.todayhouse.src.cart.model.Cart;
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
public class Product extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @Column(nullable = false)
    private String shopName;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;
    @Embedded
    private Cost cost;
    private float grade = 0.0F;
    @Column(length = 60)
    @Enumerated(EnumType.STRING)
    private Category category;
    private boolean deleted;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Builder.Default
    @JsonManagedReference
    private List<ProductImage> productImages = new ArrayList<>();
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @JsonBackReference
    private List<Scrap> scraps = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    @Builder.Default
    @ToString.Exclude
    @JsonBackReference
    private List<Cart> carts = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    @Builder.Default
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonBackReference
    private Users users;

    public void setUsers(Users users){
        this.users = users;
        users.getProducts().add(this);
    }
}
