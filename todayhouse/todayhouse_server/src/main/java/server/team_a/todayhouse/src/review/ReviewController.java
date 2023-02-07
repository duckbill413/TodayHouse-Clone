package server.team_a.todayhouse.src.review;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.team_a.todayhouse.src.review.model.GetReviewsRes;
import server.team_a.todayhouse.src.review.model.PostCreateReviewReq;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponse;
import server.team_a.todayhouse.config.BaseResponseStatus;
import server.team_a.todayhouse.utils.JwtService;

import java.util.List;


@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ReviewProvider reviewProvider;
    @Autowired
    private final ReviewService reviewService;
    @Autowired
    private final JwtService jwtService;


    public ReviewController(ReviewProvider reviewProvider, ReviewService reviewService, JwtService jwtService){
        this.reviewProvider = reviewProvider;
        this.reviewService = reviewService;
        this.jwtService = jwtService;
    }


    // 나의 리뷰 조회
    @ResponseBody
    @GetMapping("/my-reviews")  // /api/reviews
    public BaseResponse<List<GetReviewsRes>> getReviews() {
        try {

            int userIdxByJwt = jwtService.getUserIdx();

            List<GetReviewsRes> result =  reviewProvider.getReviews(userIdxByJwt);

            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }
    }
    
    // 리뷰 생성
    @ResponseBody
    @PostMapping
    public BaseResponse<String> createReview(@RequestParam(required = true) int productIdx , @RequestBody PostCreateReviewReq req) {
        try{

            if (req.getGrade() == null) {
                throw new BaseException(BaseResponseStatus.POST_REVIEW_EMPTY_GRADE);
            }

            if (req.getBody() == null) {
                throw new BaseException(BaseResponseStatus.POST_REVIEW_EMPTY_BODY);
            }

            if (!reviewProvider.isProductExists(productIdx)) {
                throw new BaseException(BaseResponseStatus.POST_REVIEW_PRODUCTID_WRONG);
            }


            int userIdx = jwtService.getUserIdx();

            int result = reviewService.createReview(userIdx, productIdx, req);

            return new BaseResponse<>("리뷰를 작성했습니다");


        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 리뷰 수정
    @ResponseBody
    @PatchMapping("/{reviewIdx}")
    public BaseResponse<String> modifyReview(@PathVariable("reviewIdx") int reviewIdx, @RequestBody PostCreateReviewReq req) {
        try{

            int userIdx = jwtService.getUserIdx();


            if (!reviewProvider.checkReviewAndUserId(reviewIdx, userIdx)) {
                throw new BaseException(BaseResponseStatus.PATCH_REVIEW_WRONG);
            }


            int result = reviewService.modifyReview(userIdx, reviewIdx, req);


            return new BaseResponse<>("리뷰를 수정했습니다");


        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    // 제품의 리뷰 조회

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetReviewsRes>> getProductReviews(@RequestParam(required = true) int productIdx) {
        try{

            if (!reviewProvider.isProductExists(productIdx)) {
                return new BaseResponse<>(BaseResponseStatus.POST_REVIEW_PRODUCTID_WRONG);
            }

            List<GetReviewsRes> result = reviewProvider.getProductReviews(productIdx);
            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));

        }
    }


}


