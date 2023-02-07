package server.team_a.todayhouse.src.order;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponseStatus;
import server.team_a.todayhouse.repository.CartRepository;
import server.team_a.todayhouse.repository.OrderRepository;
import server.team_a.todayhouse.repository.UsersRepository;
import server.team_a.todayhouse.src.cart.model.Cart;
import server.team_a.todayhouse.src.order.model.GetOrderedRes;
import server.team_a.todayhouse.src.order.model.Order;
import server.team_a.todayhouse.src.order.model.OrderSelectedReq;
import server.team_a.todayhouse.src.order.model.Shipping;
import server.team_a.todayhouse.src.users.model.Users;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderDTO {
    private final UsersRepository usersRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public GetOrderedRes orderSelectedProductInCart(OrderSelectedReq orderSelectedReq, Long userIdx) throws BaseException {
        Users users = usersRepository.findById(userIdx).orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_FIND_USER));
        if (users.getAddress() == null) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_FIND_USER_ADDRESS);
        }
        Order order = new Order();
        order.setAddress(users.getAddress());
        order.setUsers(users);

        try {
            for (Long cartIdx : orderSelectedReq.getCartList()) {
                Cart cart = cartRepository.findByIdxAndUsersIdx(cartIdx, userIdx).orElseThrow(() ->
                        new BaseException(BaseResponseStatus.FAILED_TO_FIND_CART));
                order.getCarts().add(cart);
                cart.setOrdered(true);
                cartRepository.save(cart);
            }
        } catch (BaseException e) {
            throw e;
        }
        order.initTotalCost();

        Order savedOrder = orderRepository.save(order);
        GetOrderedRes orderedRes = modelMapper.map(savedOrder, GetOrderedRes.class);
        return orderedRes;
    }

    @Transactional
    public GetOrderedRes orderAllProductInCart(Long userIdx) throws BaseException {
        Users users = usersRepository.findById(userIdx).orElseThrow(() ->
                new BaseException(BaseResponseStatus.FAILED_TO_FIND_USER));
        if (users.getAddress() == null) {
            throw new BaseException(BaseResponseStatus.FAILED_TO_FIND_USER_ADDRESS);
        }
        Order newOrder = new Order();
        newOrder.setAddress(users.getAddress());
        newOrder.setUsers(users);

        List<Cart> carts = cartRepository.findByUsersIdxAndOrdered(userIdx, false).orElseThrow(() ->
                new BaseException(BaseResponseStatus.FAILED_TO_FIND_CART));
        carts.forEach(cart -> {
            newOrder.getCarts().add(cart);
            cart.setOrdered(true);
            cartRepository.save(cart);
        });
        newOrder.initTotalCost();

        Order savedOrder = orderRepository.save(newOrder);
        GetOrderedRes orderedRes = modelMapper.map(savedOrder, GetOrderedRes.class);
        return orderedRes;
    }

    @Transactional
    public List<GetOrderedRes> loadAllOrders(Long userIdx) throws BaseException {
        List<Order> orders = orderRepository.findByUsersIdx(userIdx).orElseThrow(() ->
                new BaseException(BaseResponseStatus.FAILED_TO_FIND_ORDER));
        List<GetOrderedRes> orderedRes = new ArrayList<>();
        orders.forEach(order -> {
            GetOrderedRes getOrderedRes = modelMapper.map(order, GetOrderedRes.class);
            orderedRes.add(getOrderedRes);
        });
        return orderedRes;
    }

    @Transactional
    public void deleteOrder(Long orderIdx, Long usersIdx) throws BaseException {
        Order order = orderRepository.findByIdxAndUsersIdx(orderIdx, usersIdx).orElseThrow(() ->
                new BaseException(BaseResponseStatus.FAILED_TO_FIND_ORDER));
        if (order.isDetermine())
            throw new BaseException(BaseResponseStatus.FAILED_TO_REFUND);
        else {
            order.setOrdered(false);
            orderRepository.save(order);
        }
    }

    @Transactional
    public GetOrderedRes changeShippingStatus(Long orderIdx, Shipping shipping, Long usersIdx) throws BaseException {
        Order order = orderRepository.findByIdxAndUsersIdx(orderIdx, usersIdx).orElseThrow(() ->
                new BaseException(BaseResponseStatus.FAILED_TO_FIND_ORDER));
        order.setShipping(shipping);
        Order ordered = orderRepository.save(order);
        GetOrderedRes getOrderedRes = modelMapper.map(ordered, GetOrderedRes.class);
        return getOrderedRes;
    }

    @Transactional
    public GetOrderedRes firmOrder(Long orderIdx, Long usersIdx) throws BaseException {
        Order order = orderRepository.findByIdxAndUsersIdx(orderIdx, usersIdx).orElseThrow(() ->
                new BaseException(BaseResponseStatus.FAILED_TO_FIND_ORDER));
        order.setShipping(Shipping.SHIPPED);
        order.setDetermine(true);
        Order ordered = orderRepository.save(order);
        GetOrderedRes getOrderedRes = modelMapper.map(ordered, GetOrderedRes.class);
        return getOrderedRes;
    }
}
