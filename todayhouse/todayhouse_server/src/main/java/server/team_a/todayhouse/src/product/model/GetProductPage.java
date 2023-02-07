package server.team_a.todayhouse.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductPage {
    private long page;
    private boolean hasNext;
    private List<GetProductGrid> products;
}
