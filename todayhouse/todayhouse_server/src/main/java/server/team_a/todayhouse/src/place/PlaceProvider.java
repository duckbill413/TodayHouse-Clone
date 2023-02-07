package server.team_a.todayhouse.src.place;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.src.place.model.GetMyPlacesRes;
import server.team_a.todayhouse.src.place.model.GetPlaceDetailRes;
import server.team_a.todayhouse.src.place.model.GetPlacesRes;
import server.team_a.todayhouse.utils.JwtService;

import java.util.List;

import static server.team_a.todayhouse.config.BaseResponseStatus.DATABASE_ERROR;

//Provider : Read의 비즈니스 로직 처리
@Service
public class PlaceProvider {

    private final PlaceDao placeDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PlaceProvider(PlaceDao placeDao, JwtService jwtService) {
        this.placeDao = placeDao;
        this.jwtService = jwtService;
    }


    public List<GetPlacesRes> getPlaces(int userIdx) throws BaseException {
        try {

            List<GetPlacesRes> result = placeDao.getPlaces(userIdx);

            return result;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetPlaceDetailRes getPlaceDetail(int placeIdx, int userIdx) throws BaseException {
        try {

            GetPlaceDetailRes result = placeDao.getPlaceDetail(placeIdx, userIdx);

            return result;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public List<GetMyPlacesRes> getMyPlaces(int userIdx) throws BaseException {
        try {

            List<GetMyPlacesRes> result = placeDao.getMyPlaces(userIdx);

            return result;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean isPlaceIdxExists(int placeIdx) throws BaseException{
        try {

            return placeDao.isPlaceIdxExists(placeIdx);


        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
