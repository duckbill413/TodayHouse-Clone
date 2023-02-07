package server.team_a.todayhouse.src.scrap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponseStatus;
import server.team_a.todayhouse.repository.PlaceRepository;
import server.team_a.todayhouse.repository.ProductRepository;
import server.team_a.todayhouse.repository.ScrapRepository;
import server.team_a.todayhouse.repository.UsersRepository;
import server.team_a.todayhouse.src.place.model.Place;
import server.team_a.todayhouse.src.product.model.GetProductGrid;
import server.team_a.todayhouse.src.product.model.Product;
import server.team_a.todayhouse.src.scrap.model.GetScrapRes;
import server.team_a.todayhouse.src.scrap.model.Scrap;
import server.team_a.todayhouse.src.users.model.Users;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ScrapDTO {
    private final ScrapRepository scrapRepository;
    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public Boolean toggleScrapByProduct(Long productIdx, Long userIdx) {
        Optional<Scrap> result = scrapRepository.findByProductIdxAndUsersIdx(productIdx, userIdx);
        if (result.isPresent()){
            Scrap scrap = result.get();
            scrap.changeStatus();
            return scrapRepository.save(scrap).isStatus();
        }
        Scrap scrap = new Scrap();
        scrap.setStatus(true);
        Product product = productRepository.findById(productIdx).orElseThrow(RuntimeException::new);
        Users users = usersRepository.findById(userIdx).orElseThrow(RuntimeException::new);

        scrap.setProduct(product);
        scrap.setUsers(users);

        return scrapRepository.save(scrap).isStatus();
    }
    @Transactional
    public Boolean toggleScrapByPlace(Long placeIdx, Long userIdx) {
        Optional<Scrap> result = scrapRepository.findByPlaceIdxAndUsersIdx(placeIdx, userIdx);
        if (result.isPresent()){
            Scrap scrap = result.get();
            scrap.changeStatus();
            return scrapRepository.save(scrap).isStatus();
        }
        Scrap scrap = new Scrap();
        scrap.setStatus(true);

        Place place = placeRepository.findById(placeIdx).orElseThrow(RuntimeException::new);
        Users users = usersRepository.findById(userIdx).orElseThrow(RuntimeException::new);

        scrap.setPlace(place);
        scrap.setUsers(users);

        return scrapRepository.save(scrap).isStatus();
    }

    public GetScrapRes getMyScraps(Long userIdx) throws BaseException {
        List<Scrap> scraps = usersRepository.findById(userIdx).get().getScraps();
        if (scraps == null)
            throw new BaseException(BaseResponseStatus.SCRAP_NOT_FIND);

        GetScrapRes getScrapRes = new GetScrapRes();
        List<GetProductGrid> productLists = new ArrayList<>();

        scraps.forEach(scrap -> {
            if (scrap.getProduct() != null){
                Product product = scrap.getProduct();

                GetProductGrid p = GetProductGrid.builder()
                        .idx(product.getIdx())
                        .grade(product.getGrade())
                        .title(product.getTitle())
                        .discountRatio(product.getCost().getDiscount() / product.getCost().getCost() * 100)
                        .total(product.getCost().getCost() - product.getCost().getDiscount())
                        .reviewCount(product.getReviews().size()).build();

                p.setScapped(true);
                if (product.getProductImages().size() > 0)
                    p.setProductImage(product.getProductImages().get(0));
                productLists.add(p);
            }
            if (scrap.getPlace() != null) {
                // FEAT: Place 추가
            }
        });
        getScrapRes.setProductLists(productLists);
        return getScrapRes;
    }
}
