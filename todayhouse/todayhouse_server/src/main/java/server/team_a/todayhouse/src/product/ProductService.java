package server.team_a.todayhouse.src.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.src.product.model.*;


import java.util.List;

import static server.team_a.todayhouse.config.BaseResponseStatus.*;
@Log4j2
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDTO productDTO;

    public PostProductRes postProduct(Long userIdx, PostProductReq postProductReq) throws BaseException {
        try {
            PostProductRes postProductRes = productDTO.postProduct(userIdx, postProductReq);
            return postProductRes;
        } catch (BaseException e) {
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public PostProductRes updateProduct(Long userIdx, Long postIdx, PostProductReq postProductReq) throws BaseException {
        try {
            PostProductRes postProductRes = productDTO.updateProduct(userIdx, postIdx, postProductReq);
            return postProductRes;
        } catch (BaseException e){
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean deleteProduct(Long userIdx, Long postIdx) throws BaseException {
        try {
            boolean result = productDTO.deleteProduct(userIdx, postIdx);
            return result;
        } catch (BaseException e){
            throw e;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetProductPage getProductGridList(String category, Integer page, Long userIdx) throws BaseException {
        try {
            GetProductPage result = productDTO.getProductGridList(category.toUpperCase(), page, userIdx);
            return result;
        } catch (BaseException e){
            throw e;
        } catch (Exception e){
            e.printStackTrace();
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetProductInfo getProductInfo(Long productIdx) throws BaseException {
        try {
            GetProductInfo result = productDTO.getProductInfo(productIdx);
            return result;
        } catch (BaseException e){
            throw e;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
