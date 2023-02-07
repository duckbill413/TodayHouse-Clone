package server.team_a.todayhouse.src.base.embedded;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Cost {
    private long cost;
    private long discount;
    private long delivery;
    private long total;

    public Cost(long cost, long discount, long delivery){
        this.cost = cost;
        this.discount = discount;
        this.delivery = delivery;
        this.total = cost + delivery - discount;
    }
}
