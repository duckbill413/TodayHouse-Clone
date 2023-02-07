package server.team_a.todayhouse.src.users.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Where;
import server.team_a.todayhouse.src.Heart.model.Heart;
import server.team_a.todayhouse.src.cart.model.Cart;
import server.team_a.todayhouse.src.base.embedded.Address;
import server.team_a.todayhouse.src.order.model.Order;
import server.team_a.todayhouse.src.place.model.Place;
import server.team_a.todayhouse.src.product.model.Product;
import server.team_a.todayhouse.src.review.model.Review;
import server.team_a.todayhouse.src.scrap.model.Scrap;
import server.team_a.todayhouse.src.base.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Where(clause = "deleted = false")
@Table(name = "user")
public class Users extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(length = 60)
    private String nickname;
    private String shopName;
    @Embedded
    private Address address;
    @Column(length = 60)
    private String phoneNumber;
    @Column(length = 500)
    private String profileImg;
    @Column(length = 500)
    private String thumbnailImage;
    @Column(length = 200)
    private String introduce;
    private boolean deleted;
    private boolean social;
    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<UserRole> roleSet = new HashSet<>();
    @OneToMany(mappedBy = "users")
    @Builder.Default
    @JsonManagedReference
    private List<Cart> carts = new ArrayList<>();
    @OneToMany(mappedBy = "users")
    @Builder.Default
    @JsonManagedReference
    private List<Review> reviews = new ArrayList<>();
    @OneToMany(mappedBy = "users")
    @Builder.Default
    @JsonManagedReference
    private List<Order> orders = new ArrayList<>();
    @OneToMany(mappedBy = "users")
    @Builder.Default
    @JsonManagedReference
    private List<Place> places = new ArrayList<>();
    @OneToMany(mappedBy = "users")
    @Builder.Default
    @JsonManagedReference
    private List<Heart> hearts = new ArrayList<>();
    @OneToMany(mappedBy = "users")
    @Builder.Default
    @JsonManagedReference
    private List<Scrap> scraps = new ArrayList<>();
    @OneToMany(mappedBy = "users")
    @Builder.Default
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();
    public void addRole(UserRole userRole) {
        this.roleSet.add(userRole);
    }
    public void clearRole() {
        this.roleSet.clear();
    }
}