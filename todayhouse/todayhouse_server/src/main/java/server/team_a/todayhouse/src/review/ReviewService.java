package server.team_a.todayhouse.src.review;



import server.team_a.todayhouse.src.review.model.PostCreateReviewReq;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static server.team_a.todayhouse.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class ReviewService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewDao reviewDao;
    private final ReviewProvider reviewProvider;
    private final JwtService jwtService;


    @Autowired
    public ReviewService(ReviewDao reviewDao, ReviewProvider reviewProvider, JwtService jwtService) {
        this.reviewDao = reviewDao;
        this.reviewProvider = reviewProvider;
        this.jwtService = jwtService;

    }

    public int createReview(int userIdx, int productIdx, PostCreateReviewReq req) throws BaseException {

        int result = 0;

        boolean isExists = false;

        try {
            isExists = reviewDao.isReviewExists(userIdx, productIdx);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

        if (isExists) {
            throw new BaseException(POST_REVIEW_EXIST);
        }


        try {
            result = reviewDao.createReview(userIdx, productIdx, req);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

        if (result == 0) {
            throw new BaseException(CREATE_FAIL_REVIEW);
        } else {
            return result;
        }

    }

    public int modifyReview(int userIdx, int reviewIdx, PostCreateReviewReq req) throws BaseException {
        int result = 0;

        try {
            result = reviewDao.modifyReview(userIdx, reviewIdx, req);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

        if (result == 0) {
            throw new BaseException(MODIFY_FAIL_REVIEW);
        }

        return result;
    }

}
