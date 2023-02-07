package server.team_a.todayhouse.src.product;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponse;
import server.team_a.todayhouse.security.dto.UsersSecurityDTO;
import server.team_a.todayhouse.src.base.validation.EnumValid;
import server.team_a.todayhouse.src.product.model.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ApiOperation(value = "판매자의 상품 등록")
    @PreAuthorize("hasAnyAuthority('ROLE_SELLER')")
    @PostMapping("")
    public BaseResponse<PostProductRes> postProduct(@Valid @RequestBody PostProductReq postProductReq,
                                                    @AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        PostProductRes postProductRes = productService.postProduct(usersSecurityDTO.getIdx(), postProductReq);
        return new BaseResponse<>(postProductRes);
    }

    @ApiOperation(value = "판매자의 상품 수정")
    @PreAuthorize("hasAnyAuthority('ROLE_SELLER')")
    @PutMapping("/{product-idx}")
    public BaseResponse<PostProductRes> updateProduct(@Valid @RequestBody PostProductReq postProductReq,
                                                      @PathVariable("post-idx") Long postIdx,
                                                      @AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        PostProductRes postProductRes = productService.updateProduct(usersSecurityDTO.getIdx(), postIdx, postProductReq);
        return new BaseResponse<>(postProductRes);
    }

    @ApiOperation(value = "판매자의 판매 게시물 삭제")
    @PreAuthorize("hasAnyAuthority('ROLE_SELLER')")
    @DeleteMapping("/{product-idx}")
    public BaseResponse<String> deleteProduct(@PathVariable("product-idx") Long postIdx,
                                              @AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        boolean result = productService.deleteProduct(usersSecurityDTO.getIdx(), postIdx);
        if (result)
            return new BaseResponse<>(postIdx + "번 게시물을 삭제했습니다.");
        else
            return new BaseResponse<>(postIdx + "번 게시물 삭제에 실패했습니다.");
    }

    @ApiOperation(value = "판매자의 게시물 보기")
    @PreAuthorize("permitAll()")
    @GetMapping("/grid")
    public BaseResponse<GetProductPage> getProductGridList(@Valid @RequestParam
                                                                 @EnumValid(enumClass = Category.class, ignoreCase = true, message = "카테고리에 없는 값입니다.")
                                                                 String category,
                                                                 @RequestParam(defaultValue = "0") Integer page,
                                                                 @AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        GetProductPage result = productService.getProductGridList(category, page, usersSecurityDTO.getIdx());
        return new BaseResponse<>(result);
    }

    @ApiOperation(value = "상품 상세정보 보기")
    @PreAuthorize("permitAll()")
    @GetMapping("/search/{product-idx}")
    public BaseResponse<GetProductInfo> getProductInfo(@PathVariable(value = "product-idx") Long productIdx) throws BaseException {
        GetProductInfo product = productService.getProductInfo(productIdx);
        return new BaseResponse<>(product);
    }
}
