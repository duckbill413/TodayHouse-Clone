package server.team_a.todayhouse.src.Heart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.team_a.todayhouse.src.Heart.model.GetMyHeartsRes;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponse;
import server.team_a.todayhouse.config.BaseResponseStatus;
import server.team_a.todayhouse.utils.JwtService;

import java.util.List;

@RestController
@RequestMapping("/api/hearts")
public class HeartController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final HeartProvider heartProvider;
    @Autowired
    private final HeartService heartService;
    @Autowired
    private final JwtService jwtService;


    public HeartController(HeartProvider heartProvider, HeartService heartService, JwtService jwtService){
        this.heartProvider = heartProvider;
        this.heartService = heartService;
        this.jwtService = jwtService;
    }

    // 좋아요
    @ResponseBody
    @PostMapping("/places/{placeIdx}")
    public BaseResponse<String> addPlaceHeart(@PathVariable("placeIdx") int placeIdx) {

        try {

            int userIdx = jwtService.getUserIdx();

            if (!heartProvider.isPlaceIdxExists(placeIdx)) {
                return new BaseResponse<>(BaseResponseStatus.GET_PLACEIDX_WRONG);
            }

            int result = heartService.addPlaceHeart(userIdx, placeIdx);

            if (result == 0) {
                return new BaseResponse<>(BaseResponseStatus.ADD_FAIL_PLACE_HEART);
            }

            return new BaseResponse<>("공간 게시물에 좋아요를 눌렀습니다.");



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    
    // 좋아요 취소
    @ResponseBody
    @PatchMapping("/places/{placeIdx}")
    public BaseResponse<String> removePlaceHeart(@PathVariable("placeIdx") int placeIdx) {
        try {

            int userIdx = jwtService.getUserIdx();

            if (!heartProvider.isPlaceIdxExists(placeIdx)) {
                return new BaseResponse<>(BaseResponseStatus.GET_PLACEIDX_WRONG);
            }

            int result = heartService.removePlaceHeart(userIdx, placeIdx);

            if (result == 0) {
                throw new BaseException(BaseResponseStatus.REMOVE_FAIL_PLACE_HEART);
            }

            return new BaseResponse<>("공간 게시물에 좋아요를 해제했습니다.");



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/my-hearts")
    public BaseResponse<List<GetMyHeartsRes>> getMyHearts() {
        try {


            int userIdx = jwtService.getUserIdx();

            List<GetMyHeartsRes> result = heartProvider.getMyHearts(userIdx);

            return new BaseResponse<>(result);


        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }




}


