package server.team_a.todayhouse.src.Heart;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.team_a.todayhouse.src.Heart.model.GetMyHeartsRes;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.utils.JwtService;

import java.util.List;

import static server.team_a.todayhouse.config.BaseResponseStatus.DATABASE_ERROR;

//Provider : Read의 비즈니스 로직 처리
@Service
public class HeartProvider {

    private final HeartDao heartDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public HeartProvider(HeartDao heartDao, JwtService jwtService) {
        this.heartDao = heartDao;
        this.jwtService = jwtService;
    }

    public List<GetMyHeartsRes> getMyHearts(int userIdx) throws BaseException {

        try {

            List<GetMyHeartsRes> result = heartDao.getMyHearts(userIdx);

            return result;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }



    }

    public boolean isPlaceIdxExists(int placeIdx) throws BaseException{
        try {

            return heartDao.isPlaceIdxExists(placeIdx);


        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
