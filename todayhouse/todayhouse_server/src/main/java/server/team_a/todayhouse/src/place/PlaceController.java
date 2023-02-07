package server.team_a.todayhouse.src.place;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.team_a.todayhouse.src.place.model.GetMyPlacesRes;
import server.team_a.todayhouse.src.place.model.GetPlaceDetailRes;
import server.team_a.todayhouse.src.place.model.GetPlacesRes;
import server.team_a.todayhouse.src.place.model.PatchPlaceReq;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponse;
import server.team_a.todayhouse.config.BaseResponseStatus;
import server.team_a.todayhouse.utils.JwtService;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceProvider placeProvider;
    private final PlaceService placeService;
    private final JwtService jwtService;

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetPlacesRes>> getPlaces() {
        try {
            int userIdx = -1;
            if (jwtService.getJwt() != null) {
                userIdx = jwtService.getUserIdx();
            }

            List<GetPlacesRes> result = placeProvider.getPlaces(userIdx);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {

            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @GetMapping("{placeIdx}")
    public BaseResponse<GetPlaceDetailRes> getPlaceDetail(@PathVariable("placeIdx") int placeIdx) {
        try {
            int userIdx = -1;
            if (jwtService.getJwt() != null) {
                userIdx = jwtService.getUserIdx();
            }

            if (!placeProvider.isPlaceIdxExists(placeIdx)) {
                return new BaseResponse<>(BaseResponseStatus.GET_PLACEIDX_WRONG);
            }

            GetPlaceDetailRes result = placeProvider.getPlaceDetail(placeIdx, userIdx);

            return new BaseResponse<>(result);



        } catch (BaseException exception) {

            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("{placeIdx}")
    public BaseResponse<String> modifyPlace(@RequestBody PatchPlaceReq req, @PathVariable("placeIdx") int placeIdx) {
        try {

            int userIdx = jwtService.getUserIdx();

            if (!placeProvider.isPlaceIdxExists(placeIdx)) {
                return new BaseResponse<>(BaseResponseStatus.GET_PLACEIDX_WRONG);
            }


            int result = placeService.modifyPlace(userIdx, placeIdx ,req);



            return new BaseResponse<>("공간 게시물을 수정했습니다.");


        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }



    @ResponseBody
    @GetMapping("/my-places")
    public BaseResponse<List<GetMyPlacesRes>> getMyPlaces() {
        try {
            int userIdx = jwtService.getUserIdx();

            List<GetMyPlacesRes> result = placeProvider.getMyPlaces(userIdx);

            return new BaseResponse<>(result);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


}


