package server.team_a.todayhouse.src.cart;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponse;
import server.team_a.todayhouse.security.dto.UsersSecurityDTO;
import server.team_a.todayhouse.src.cart.model.Cart;
import server.team_a.todayhouse.src.cart.model.GetMyCart;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @ApiOperation(value = "장바구니에 상품 추가")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{product-idx}")
    public BaseResponse<String> addProductInCart(@PathVariable("product-idx") Long productIdx,
                                                     @RequestParam(name = "count", defaultValue = "1") Long count,
                                                     @AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        cartService.addProductInCart(productIdx, count, usersSecurityDTO.getIdx());
        return new BaseResponse<>("장바구니에 상품 추가 완료");
    }
    @ApiOperation(value = "장바구니 상품 삭제")
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{cart-idx}")
    public BaseResponse<GetMyCart> deleteProductInCart(@PathVariable("cart-idx") Long cartIdx,
                                                       @AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        GetMyCart result = cartService.deleteProductInCart(cartIdx, usersSecurityDTO.getIdx());
        return new BaseResponse<>(result);
    }
    @ApiOperation(value = "장바구니 상품 확인")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/my-cart")
    public BaseResponse<GetMyCart> searchMyCarts(@AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        GetMyCart result = cartService.searchMyCarts(usersSecurityDTO.getIdx());
        return new BaseResponse<>(result);
    }
    @ApiOperation(value = "장바구니 전체 삭제")
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/clear")
    public BaseResponse<GetMyCart> clearMyCarts(@AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        GetMyCart result = cartService.clearMyCarts(usersSecurityDTO.getIdx());
        return new BaseResponse<>(result);
    }
}
