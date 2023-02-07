package server.team_a.todayhouse.src.scrap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponse;
import server.team_a.todayhouse.config.BaseResponseStatus;
import server.team_a.todayhouse.src.scrap.model.GetScrapRes;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapService {
    private final ScrapDTO scrapDTO;
    public Boolean toggleScrapByProduct(Long productIdx, Long userIdx) throws BaseException {
        try {
            return scrapDTO.toggleScrapByProduct(productIdx, userIdx);
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public Boolean toggleScrapByPlace(Long placeIdx, Long userIdx) throws BaseException {
        try {
            return scrapDTO.toggleScrapByPlace(placeIdx, userIdx);
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public GetScrapRes getMyScraps(Long userIdx) throws BaseException {
        try {
            return scrapDTO.getMyScraps(userIdx);
        } catch (BaseException e){
            throw e;
        } catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
