package server.team_a.todayhouse.src.scrap.model;

import lombok.*;
import server.team_a.todayhouse.src.product.model.GetProductGrid;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GetScrapRes {
    List<GetProductGrid> productLists;
}
