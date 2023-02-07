package server.team_a.todayhouse.src.scrap;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponse;
import server.team_a.todayhouse.security.dto.UsersSecurityDTO;
import server.team_a.todayhouse.src.scrap.model.GetScrapRes;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/scrap")
@RequiredArgsConstructor
public class ScrapController {
    private final ScrapService scrapService;

    @ApiOperation(value = "상품 스크랩")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/product/{product-idx}")
    public BaseResponse<String> toggleScrapByProduct(@PathVariable("product-idx") Long productIdx,
                                                     @AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        Boolean result = scrapService.toggleScrapByProduct(productIdx, usersSecurityDTO.getIdx());
        String message = result ? "스크랩 등록 완료" : "스크랩 취소 완료";
        return new BaseResponse<>(message);
    }

    @ApiOperation(value = "둘러보기 스크랩")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/place/{place-idx}")
    public BaseResponse<String> toggleScrapByPlace(@PathVariable("place-idx") Long placeIdx,
                                                   @AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        Boolean result = scrapService.toggleScrapByPlace(placeIdx, usersSecurityDTO.getIdx());
        String message = result ? "스크랩 등록 완료" : "스크랩 취소 완료";
        return new BaseResponse<>(message);
    }

    @ApiOperation(value = "스크랩 정보 보기")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/my-scrap")
    public BaseResponse<GetScrapRes> getMyScraps(@AuthenticationPrincipal UsersSecurityDTO usersSecurityDTO) throws BaseException {
        GetScrapRes result = scrapService.getMyScraps(usersSecurityDTO.getIdx());
        return new BaseResponse<>(result);
    }
}
