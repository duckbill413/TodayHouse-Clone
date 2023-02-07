package server.team_a.todayhouse.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import server.team_a.todayhouse.src.image.model.ProductImage;
import server.team_a.todayhouse.src.product.model.Category;
import server.team_a.todayhouse.src.product.model.Product;
import server.team_a.todayhouse.src.users.model.Users;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProductImageRepository productImageRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void productTest() {
        Product product = Product.builder()
                .title("상품명")
                .body("상품설명")
                .category(Category.DECO)
                .build();

        ProductImage productImage1 = new ProductImage();
        productImage1.setSequence(1);
        productImage1.setImageUrl("image1URL");
        productImage1.setProduct(product);
        ProductImage productImage2 = new ProductImage();
        productImage2.setSequence(2);
        productImage2.setImageUrl("image2URL");
        ProductImage productImage3 = new ProductImage();
        productImage3.setSequence(3);
        productImage3.setImageUrl("image23RL");

        product.getProductImages().add(productImage1);
        product.getProductImages().add(productImage2);
        product.getProductImages().add(productImage3);

        Product p = productRepository.save(product);

        System.out.println(">>>>: " + p);
    }
}