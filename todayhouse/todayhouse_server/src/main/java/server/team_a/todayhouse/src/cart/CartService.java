package server.team_a.todayhouse.src.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponseStatus;
import server.team_a.todayhouse.src.cart.model.Cart;
import server.team_a.todayhouse.src.cart.model.GetMyCart;
import server.team_a.todayhouse.src.users.model.Users;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartDTO cartDTO;

    public void addProductInCart(Long productIdx, Long count, Long userIdx) throws BaseException {
        try {
            cartDTO.addProductInCart(productIdx, count, userIdx);
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public GetMyCart deleteProductInCart(Long cartIdx, Long userIdx) throws BaseException {
        try {
            GetMyCart result = cartDTO.deleteProductInCart(cartIdx, userIdx);
            return result;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public GetMyCart searchMyCarts(Long userIdx) throws BaseException {
        try {
            GetMyCart result = cartDTO.searchMyCarts(userIdx);
            return result;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public GetMyCart clearMyCarts(Long userIdx) throws BaseException {
        try {
            GetMyCart result = cartDTO.clearMyCarts(userIdx);
            return result;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
