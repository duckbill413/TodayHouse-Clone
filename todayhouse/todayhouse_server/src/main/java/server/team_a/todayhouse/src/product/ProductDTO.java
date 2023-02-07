package server.team_a.todayhouse.src.product;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponseStatus;
import server.team_a.todayhouse.repository.*;
import server.team_a.todayhouse.src.base.embedded.Cost;
import server.team_a.todayhouse.src.image.model.ProductImage;
import server.team_a.todayhouse.src.product.model.*;
import server.team_a.todayhouse.src.review.model.Review;
import server.team_a.todayhouse.src.review.model.ReviewGrade;
import server.team_a.todayhouse.src.users.model.Users;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class ProductDTO {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final UsersRepository usersRepository;
    private final ScrapRepository scrapRepository;
    private final ReviewRepository reviewRepository;
    private final EntityManager entityManager;

    @org.springframework.transaction.annotation.Transactional(isolation = Isolation.DEFAULT)
    public PostProductRes postProduct(Long userIdx, PostProductReq postProductReq) throws BaseException {
        Product product = modelMapper.map(postProductReq, Product.class);
        postProductReq.getImages().forEach(postProductImageReq -> {
            ProductImage p = modelMapper.map(postProductImageReq, ProductImage.class);
            p.setProduct(product);
        });
        Users users = usersRepository.findById(userIdx).orElseThrow(() ->
                new BaseException(BaseResponseStatus.FAILED_TO_FIND_USER));
        if (users.getShopName() == null)
            throw new BaseException(BaseResponseStatus.SHOPNAME_CANNOT_NULL);
        product.setCost(
                new Cost(postProductReq.getCost(), postProductReq.getDiscount(), postProductReq.getDelivery())
        );
        product.setUsers(users);
        users.getProducts().add(product);
        product.setShopName(users.getShopName());

        Product savedProduct = productRepository.save(product);
        PostProductRes postProductRes = modelMapper.map(savedProduct, PostProductRes.class);
        postProductRes.setCategory(savedProduct.getCategory().getKorean());

        long scrapCount = scrapRepository.countByProductIdx(savedProduct.getIdx());
        ReviewGrade reviewGrade = reviewRepository.getReviewsSumAndCount(savedProduct);

        postProductRes.setScrapCount(scrapCount);
        postProductRes.setReviewCount(reviewGrade.getCount());
        postProductRes.setGrade(reviewGrade.getSum() / reviewGrade.getCount());

        return postProductRes;
    }

    @Transactional
    public PostProductRes updateProduct(Long userIdx, Long postIdx, PostProductReq postProductReq) throws BaseException {
            Product product = productRepository.findByIdxAndUsersIdx(postIdx, userIdx).orElseThrow(() ->
                    new BaseException(BaseResponseStatus.USERS_PRODUCT_IS_NOT_FOUND));

            product.getProductImages().forEach(productImage -> productImage.setDeleted(true));

            product.setTitle(postProductReq.getTitle());
            product.setBody(postProductReq.getBody());
            Cost cost = new Cost(postProductReq.getCost(), postProductReq.getDiscount(), postProductReq.getDelivery());
            product.setCost(cost);
            product.setCategory(Enum.valueOf(Category.class, postProductReq.getCategory().toUpperCase()));
            // Product 이미지 수정
            postProductReq.getImages().forEach(postProductImageReq -> {
            ProductImage p = modelMapper.map(postProductImageReq, ProductImage.class);
            p.setProduct(product);
        });

        Product savedProduct = productRepository.save(product);

        PostProductRes postProductRes = modelMapper.map(savedProduct, PostProductRes.class);
        postProductRes.setCategory(savedProduct.getCategory().getKorean());
        List<ProductImage> images = productImageRepository.findByProductIdx(savedProduct.getIdx()).orElse(null);
        postProductRes.setImages(images);

        // 평점 입력
        long scrapCount = scrapRepository.countByProductIdx(savedProduct.getIdx());
        ReviewGrade reviewGrade = reviewRepository.getReviewsSumAndCount(savedProduct);

        postProductRes.setScrapCount(scrapCount);
        postProductRes.setReviewCount(reviewGrade.getCount());
        postProductRes.setGrade(reviewGrade.getSum() / reviewGrade.getCount());
        return postProductRes;
    }

    @Transactional
    public boolean deleteProduct(Long userIdx, Long postIdx) throws BaseException {
        Optional<Product> result = productRepository.findByIdxAndUsersIdx(postIdx, userIdx);

        Product product = result.orElseThrow(() -> new BaseException(BaseResponseStatus.USERS_PRODUCT_IS_NOT_FOUND));
        product.setDeleted(true);

        Product deletedProduct = productRepository.save(product);
        if (deletedProduct.isDeleted())
            return true;
        else return false;
    }

    @Transactional
    public GetProductPage getProductGridList(String category, Integer page, Long userIdx) throws BaseException {
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.Direction.DESC, "updatedAt");
        Category c = Enum.valueOf(Category.class, category);
        List<Product> products;
        Optional<Page<Product>> result;
        if (c == Category.ALL) {
            result = productRepository.findAllByOrderByUpdatedAtDesc(pageRequest);
        }
        else {
            result = productRepository.findByCategory(c, pageRequest);
        }
        Page<Product> pageProducts = result.orElseThrow(() -> new BaseException(BaseResponseStatus.NO_PRODUCT_IN_CATEGORY));
        int totalPages = pageProducts.getTotalPages();
        products =  pageProducts.getContent();
        List<GetProductGrid> gridProducts = new ArrayList<>();

        products.forEach(product -> {
            GetProductGrid p = GetProductGrid.builder()
                    .idx(product.getIdx())
                    .grade(product.getGrade())
                    .shopName(product.getShopName())
                    .title(product.getTitle())
                    .discountRatio((long) ((float) product.getCost().getDiscount() / product.getCost().getCost() * 100))
                    .total(product.getCost().getCost() - product.getCost().getDiscount() + product.getCost().getDelivery())
                    .reviewCount(product.getReviews().size()).build();
            if (userIdx != null && scrapRepository.existsScrapByUsersIdxAndProductIdx(userIdx, product.getIdx()))
                p.setScapped(true);
            else p.setScapped(false);
            if (product.getProductImages().size() > 0)
                p.setProductImage(product.getProductImages().get(0));
            gridProducts.add(p);
        });
        boolean hasNext = page < totalPages-1 ? true : false;
        GetProductPage getProductPage = new GetProductPage(page, hasNext, gridProducts);

        return getProductPage;
    }

    public GetProductInfo getProductInfo(Long productIdx) throws BaseException {
        Optional<Product> result = productRepository.findById(productIdx);
        Product product = result.orElseThrow(() -> new BaseException(BaseResponseStatus.FAILED_TO_LOAD_PRODUCT));

        GetProductInfo getProductInfo = modelMapper.map(product, GetProductInfo.class);
        getProductInfo.setCategory(product.getCategory().getKorean());
        Long cost = getProductInfo.getCost().getCost();
        Long delivery = getProductInfo.getCost().getDelivery();
        Long discount = getProductInfo.getCost().getDiscount();
        Long total = cost + delivery - discount;
        Integer discountRatio = (int) (discount * 100 / total);
        getProductInfo.setDiscountRatio(discountRatio);
        getProductInfo.getCost().setTotal(total);

        getProductInfo.setScrapCount(product.getScraps().size());
        getProductInfo.setReviewCount(product.getReviews().size());
        return getProductInfo;
    }
}
