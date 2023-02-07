package server.team_a.todayhouse.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import server.team_a.todayhouse.src.product.model.Product;
import server.team_a.todayhouse.src.review.model.ReviewGrade;
import server.team_a.todayhouse.src.scrap.model.Scrap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ScrapRepository scrapRepository;

    @Test
    @Transactional
    void reviewGradeTest(){
        Product product = productRepository.findById(12L).orElseThrow(RuntimeException::new);
        ReviewGrade reviewGrade = reviewRepository.getReviewsSumAndCount(product);
        System.out.println(">>>>> sum: " + reviewGrade.getSum());
        System.out.println(">>>>> count: " + reviewGrade.getCount());
        System.out.println(">>>>> grade: " + reviewGrade.getSum() / reviewGrade.getCount());

        long scrapCount = scrapRepository.countByProductIdx(1L);
        System.out.println(">>>>> scrap count: " + scrapCount);
    }
}