package server.team_a.todayhouse.src.place;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.src.place.model.PatchPlaceReq;
import server.team_a.todayhouse.utils.JwtService;

import static server.team_a.todayhouse.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class PlaceService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PlaceDao placeDao;
    private final PlaceProvider placeProvider;
    private final JwtService jwtService;


    @Autowired
    public PlaceService(PlaceDao placeDao, PlaceProvider placeProvider, JwtService jwtService) {
        this.placeDao = placeDao;
        this.placeProvider = placeProvider;
        this.jwtService = jwtService;

    }

    public int modifyPlace(int userIdx, int placeIdx, PatchPlaceReq req) throws BaseException {

        int result = 0;

        boolean haveAuth = true;

        try {
            haveAuth = placeDao.doesUserHaveAuthorityPlace(userIdx, placeIdx);
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

        if (!haveAuth) {
            throw new BaseException(PATCH_PLACE_WRONG);
        }

        try {
            result = placeDao.modifyPlace(userIdx, placeIdx, req);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

        if (result == 0) {
            throw new BaseException(MODIFY_FAIL_PLACE);
        }

        return result;
    }

}
