package server.team_a.todayhouse.src.cart;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import server.team_a.todayhouse.repository.CartRepository;
import server.team_a.todayhouse.repository.ProductRepository;
import server.team_a.todayhouse.repository.UsersRepository;
import server.team_a.todayhouse.src.cart.model.Cart;
import server.team_a.todayhouse.src.cart.model.GetMyCart;
import server.team_a.todayhouse.src.product.model.GetProductForCartRes;
import server.team_a.todayhouse.src.product.model.Product;
import server.team_a.todayhouse.src.users.model.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
@RequiredArgsConstructor
public class CartDTO {
    private final UsersRepository usersRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public void addProductInCart(Long productIdx, Long count, Long userIdx) {
        Users users = usersRepository.findById(userIdx).orElseThrow(RuntimeException::new);
        Product product = productRepository.findById(productIdx).orElseThrow(RuntimeException::new);

        Cart cart = new Cart();
        cart.setCost(product.getCost());
        cart.setProduct(product);
        cart.setUsers(users);
        cart.setCount(count);
        cart.setAmount(cart.getCost().getTotal() * count);

        cartRepository.save(cart);
    }

    @Transactional
    public GetMyCart deleteProductInCart(Long cartIdx, Long userIdx) {
        Cart cart = cartRepository.findById(cartIdx).orElseThrow(NoSuchElementException::new);
        cart.setDeleted(true);

        cartRepository.save(cart);
        return getMyCarts(userIdx);
    }

    public GetMyCart searchMyCarts(Long userIdx) {
        return getMyCarts(userIdx);
    }

    @Transactional
    public GetMyCart clearMyCarts(Long userIdx) {
        Users users = usersRepository.findById(userIdx).orElseThrow(NoSuchElementException::new);
        users.getCarts().forEach(cart -> {
            cart.setDeleted(true);
            cartRepository.save(cart);
        });

        return getMyCarts(userIdx);
    }

    @Transactional
    public GetMyCart getMyCarts(Long userIdx) {
        List<Cart> carts = cartRepository.findByUsersIdxAndOrdered(userIdx, false).orElseThrow(NoSuchElementException::new);

        GetMyCart getMyCart = new GetMyCart();
        List<GetProductForCartRes> products = new ArrayList<>();

        carts.forEach(cart -> {
            Product product = cart.getProduct();
            GetProductForCartRes p = new GetProductForCartRes();
            p.setCartIdx(cart.getIdx());
            p.setProductIdx(product.getIdx());
            p.setTitle(product.getTitle());
            p.setProductImage(product.getProductImages().get(0));
            p.setCost(product.getCost());
            p.setShopName(product.getShopName());
            p.setCount(cart.getCount());
            p.setAmount();
            products.add(p);
        });
        getMyCart.setProduct(products);
        getMyCart.initTotalCost();
        return getMyCart;
    }
}
