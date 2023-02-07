package server.team_a.todayhouse.src.users;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponse;
import server.team_a.todayhouse.security.dto.UsersSecurityDTO;
import server.team_a.todayhouse.src.users.model.*;
import server.team_a.todayhouse.utils.JWTUtil;
import server.team_a.todayhouse.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

import static server.team_a.todayhouse.config.BaseResponseStatus.*;
import static server.team_a.todayhouse.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/api/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final JWTUtil jwtUtil;

    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService, JWTUtil jwtUtil){
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 테스트용
     */
    @ResponseBody
    @GetMapping("/test") // (GET) 127.0.0.1:9000/api/users/test
    public String getUser() {
        return "테스트 성공적";

    }

    /**
     * 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/sign-up-email")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {

        if(postUserReq.getEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if(!isRegexEmail(postUserReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }

        if (postUserReq.getPassword() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_NICKNAME);
        }

        if (postUserReq.getPassword() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }



        try{
            PostUserRes postUserRes = userService.createUser(postUserReq);

            return new BaseResponse<>(postUserRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 이메일 로그인 API
     * [POST] /users/log-in
     * @return BaseResponse<PostLoginRes>
     */
    @Deprecated
    @ResponseBody
    @PostMapping("/sign-in-email")
    public BaseResponse<PostLoginRes> emailLogIn(@RequestBody PostLoginReq postLoginReq) {
        try{
            if(postLoginReq.getEmail() == null){
                return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
            }
            //이메일 정규표현
            if(!isRegexEmail(postLoginReq.getEmail())){
                return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
            }

            PostLoginRes postLoginRes = userProvider.emailLogin(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 닉네임 수정 API
     * [PATCH] /users/nickname
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/nickname")
    public BaseResponse<String> modifyUserName(@RequestBody PatchUserNickNameReq user){
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            if (userProvider.checkNickName(user.getNickname()) == 1) {
                return new BaseResponse<>(POST_USERS_EXISTS_NICKNAME);
            }

            userService.modifyUserNickName(user.getNickname(), userIdxByJwt);

            String result = "닉네임을 수정하였습니다.";
        return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 비밀번호 수정 API
     * [PATCH] /users/password
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/password")
    public BaseResponse<String> modifyUserPassWord(@RequestBody PatchUserPassWordReq user){
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            userService.modifyUserPassWord(user.getPassword(), userIdxByJwt);

            String result = "비밀번호를 수정하였습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }
    }
    /**
     * 한줄소개 수정 API
     * [PATCH] /users/introduce
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/introduce")
    public BaseResponse<String> modifyUserIntroduce(@RequestBody PatchUserIntroduceReq user){
        try {
            int userIdxByJwt = jwtService.getUserIdx();

            userService.modifyUserIntroduce(user.getIntroduce(), userIdxByJwt);

            String result = "한 줄 소개를 수정하였습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/my-page")
    public BaseResponse<GetMyPageRes> getMyPage() {
        try {

            int userIdx = jwtService.getUserIdx();

            GetMyPageRes result = userProvider.getMyPage(userIdx);

            return new BaseResponse<>(result);

        } catch (BaseException exception) {
        return new BaseResponse<>((exception.getStatus()));
        }
    }
    @ApiOperation(value = "판매자의 매장명 등록")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    @PatchMapping("/shop-name")
    public BaseResponse<String> modifyUserAddress(@Valid @RequestBody PatchUserShopnameReq shopname,
                                                  @AuthenticationPrincipal UsersSecurityDTO user) throws BaseException {
        userService.modifyUserShopName(shopname, user.getIdx());
        return new BaseResponse<>("주소를 수정하였습니다.");
    }
    @ApiOperation(value = "유저의 주소 변경")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @PatchMapping("/address")
    public BaseResponse<String> modifyUserAddress(@Valid @RequestBody PatchUserAddressReq address,
                                                  @AuthenticationPrincipal UsersSecurityDTO user) throws BaseException {
        userService.modifyUserAddress(address, user.getIdx());
        return new BaseResponse<>("주소를 수정하였습니다.");
    }
    @ApiOperation(value = "유저의 권한 변경(ADMIN 권한 필요)")
    @PostMapping("/authority")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String changeUsersAuthority(@RequestParam Long idx,
                                       @RequestParam String role,
                                       @RequestParam(required = false) String shopName) throws BaseException {
        userProvider.changeAuth(idx, role, shopName);
        return "Role changed";
    }
}
