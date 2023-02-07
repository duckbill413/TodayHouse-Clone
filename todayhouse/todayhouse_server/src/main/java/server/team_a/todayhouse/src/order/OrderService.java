package server.team_a.todayhouse.src.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponseStatus;
import server.team_a.todayhouse.src.cart.model.GetMyCart;
import server.team_a.todayhouse.src.order.model.GetOrderedRes;
import server.team_a.todayhouse.src.order.model.Order;
import server.team_a.todayhouse.src.order.model.OrderSelectedReq;
import server.team_a.todayhouse.src.order.model.Shipping;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderDTO orderDTO;

    public GetOrderedRes orderSelectedProductInCart(OrderSelectedReq orderSelectedReq, Long userIdx) throws BaseException {
        try {
            GetOrderedRes result = orderDTO.orderSelectedProductInCart(orderSelectedReq, userIdx);
            return result;
        } catch(BaseException e){
            throw e;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public GetOrderedRes orderAllProductInCart(Long userIdx) throws BaseException {
        try {
            GetOrderedRes result = orderDTO.orderAllProductInCart(userIdx);
            return result;
        } catch(BaseException e){
            throw e;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public List<GetOrderedRes> loadAllOrders(Long userIdx) throws BaseException {
        try {
            List<GetOrderedRes> result = orderDTO.loadAllOrders(userIdx);
            return result;
        } catch(BaseException e){
            throw e;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public void deleteOrder(Long orderIdx, Long usersIdx) throws BaseException {
        try {
            orderDTO.deleteOrder(orderIdx, usersIdx);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public GetOrderedRes changeShippingStatus(Long orderIdx, Shipping shipping, Long userIdx) throws BaseException {
        try {
            GetOrderedRes result = orderDTO.changeShippingStatus(orderIdx, shipping, userIdx);
            return result;
        } catch (BaseException e){
            throw e;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public GetOrderedRes firmOrder(Long orderIdx, Long usersIdx) throws BaseException {
        try {
            GetOrderedRes result = orderDTO.firmOrder(orderIdx, usersIdx);
            return result;
        } catch (BaseException e){
            throw e;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
