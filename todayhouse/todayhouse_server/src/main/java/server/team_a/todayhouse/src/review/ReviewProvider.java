package server.team_a.todayhouse.src.review;


import server.team_a.todayhouse.src.review.model.GetReviewsRes;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static server.team_a.todayhouse.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class ReviewProvider {

    private final ReviewDao reviewDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ReviewProvider(ReviewDao reviewDao, JwtService jwtService) {
        this.reviewDao = reviewDao;
        this.jwtService = jwtService;
    }


    public List<GetReviewsRes> getReviews(int userIdx) throws BaseException {

        try{
            List<GetReviewsRes> result = reviewDao.getReviews(userIdx);
            return result;
        }
        catch (Exception exception) {
            logger.error("App - getUserRes Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public List<GetReviewsRes> getProductReviews(int productIdx) throws BaseException {

        try{
            List<GetReviewsRes> result = reviewDao.getProductReviews(productIdx);
            return result;
        }
        catch (Exception exception) {
            logger.error("App - getUserRes Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public boolean isProductExists(int productIdx) throws BaseException{

        boolean result = false;

        try {
            result = reviewDao.isProductExists(productIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

        return result;
    }

    public boolean checkReviewAndUserId(int reviewIdx, int userIdx) throws BaseException {
        boolean result = false;

        try {
            result = reviewDao.checkReviewAndUserId(reviewIdx, userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

        return result;
    }

}
