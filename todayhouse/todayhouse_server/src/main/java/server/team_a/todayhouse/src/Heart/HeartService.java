package server.team_a.todayhouse.src.Heart;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.utils.JwtService;

import static server.team_a.todayhouse.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class HeartService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final HeartDao heartDao;
    private final HeartProvider heartProvider;
    private final JwtService jwtService;


    @Autowired
    public HeartService(HeartDao heartDao, HeartProvider heartProvider, JwtService jwtService) {
        this.heartDao = heartDao;
        this.heartProvider = heartProvider;
        this.jwtService = jwtService;

    }

    public int addPlaceHeart(int userIdx, int placeIdx) throws BaseException {
        try {

            int result = heartDao.addPlaceHeart(userIdx, placeIdx);

            return result;



        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int removePlaceHeart(int userIdx, int placeIdx) throws BaseException {
        try {

            int result = heartDao.removePlaceHeart(userIdx, placeIdx);


            return result;



        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
