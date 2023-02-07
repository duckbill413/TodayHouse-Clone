package server.team_a.todayhouse.src.order;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponse;
import server.team_a.todayhouse.security.dto.UsersSecurityDTO;
import server.team_a.todayhouse.src.base.validation.EnumValid;
import server.team_a.todayhouse.src.cart.model.GetMyCart;
import server.team_a.todayhouse.src.order.model.GetOrderedRes;
import server.team_a.todayhouse.src.order.model.Order;
import server.team_a.todayhouse.src.order.model.OrderSelectedReq;
import server.team_a.todayhouse.src.order.model.Shipping;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @ApiOperation(value = "장바구니 선택 상품 주문하기")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("select")
    public BaseResponse<GetOrderedRes> orderSelectedProductInCart(@Valid @RequestBody OrderSelectedReq orderSelectedReq,
                                                          @AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        GetOrderedRes order = orderService.orderSelectedProductInCart(orderSelectedReq, usersSecurityDTO.getIdx());
        return new BaseResponse<>(order);
    }

    @ApiOperation(value = "장바구니 전체 상품 주문하기")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("order-all")
    public BaseResponse<GetOrderedRes> orderAllProductInCart(@AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        GetOrderedRes order = orderService.orderAllProductInCart(usersSecurityDTO.getIdx());
        return new BaseResponse<>(order);
    }

    @ApiOperation(value = "전체 주문 확인")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("")
    public BaseResponse<List<GetOrderedRes>> loadAllOrders(@AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        List<GetOrderedRes> orders = orderService.loadAllOrders(usersSecurityDTO.getIdx());
        return new BaseResponse<>(orders);
    }

    @ApiOperation(value = "주문 환불")
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @DeleteMapping("/{order-idx}")
    public BaseResponse<String> deleteOrder(@PathVariable(value = "order-idx") Long orderIdx,
                                            @AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        orderService.deleteOrder(orderIdx, usersSecurityDTO.getIdx());
        return new BaseResponse<>("주문이 삭제 되었습니다.");
    }

    @ApiOperation(value = "주문 배송상태 변경")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("ship/{order-idx}")
    public BaseResponse<GetOrderedRes> changeShippingStatus(@PathVariable(value = "order-idx") Long orderIdx,
                                                    @RequestParam(name = "ship") @EnumValid(enumClass = Shipping.class, ignoreCase = true, message = "배송 항목에 없습니다.")
                                                    String ship,
                                                    @AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        Shipping shipping = Enum.valueOf(Shipping.class, ship);
        GetOrderedRes order = orderService.changeShippingStatus(orderIdx, shipping, usersSecurityDTO.getIdx());
        return new BaseResponse<>(order);
    }

    @ApiOperation(value = "주문 확정하기")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("confirm/{order-idx}")
    public BaseResponse<GetOrderedRes> firmOrder(@PathVariable(value = "order-idx") Long orderIdx,
                                         @AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        GetOrderedRes order = orderService.firmOrder(orderIdx, usersSecurityDTO.getIdx());
        return new BaseResponse<>(order);
    }

}
