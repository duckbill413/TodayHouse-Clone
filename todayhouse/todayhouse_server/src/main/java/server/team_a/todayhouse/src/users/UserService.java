package server.team_a.todayhouse.src.users;



import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.src.users.model.PatchUserAddressReq;
import server.team_a.todayhouse.src.users.model.PatchUserShopnameReq;
import server.team_a.todayhouse.src.users.model.PostUserReq;
import server.team_a.todayhouse.src.users.model.PostUserRes;
import server.team_a.todayhouse.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static server.team_a.todayhouse.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
@RequiredArgsConstructor
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복 이메일
        if(userProvider.checkEmail(postUserReq.getEmail()) ==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        if(userProvider.checkNickName(postUserReq.getNickname()) == 1) {
            throw new BaseException(POST_USERS_EXISTS_NICKNAME);
        }

        String pwd;
        try{
            //암호화
            pwd = passwordEncoder.encode(postUserReq.getPassword());
            postUserReq.setPassword(pwd);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try{
            PostUserRes postUserRes = userDao.createUser(postUserReq);
            return postUserRes;
        } catch (Exception exception) {
            logger.error("App - createUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserNickName(String newNickName, int userIdx) throws BaseException {
        try{
            int result = userDao.modifyUserNickName(newNickName, userIdx);
            if(result == 0){
                throw new BaseException(MODIFY_FAIL_USERNAME);
            }
        } catch(Exception exception){
            logger.error("App - modifyUserName Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserPassWord(String password, int userIdx) throws BaseException {

        int result = 1;

        try{
            System.out.println("비번 해시화1 :" + passwordEncoder.encode(password));
            System.out.println("비번 해시화2 :" + passwordEncoder.encode(password));
            result = userDao.modifyUserPassWord(passwordEncoder.encode(password), userIdx);

        } catch(Exception exception){
            logger.error("App - modifyUserName Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }

        if(result == 0){
            throw new BaseException(MODIFY_FAIL_PASSWORD);
        }

    }

    public void modifyUserIntroduce(String introduce, int userIdx) throws BaseException {

        int result = 1;

        try{

            result = userDao.modifyUserIntroduce(introduce, userIdx);

        } catch(Exception exception){
            logger.error("App - modifyUserName Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }

        if(result == 0){
            throw new BaseException(MODIFY_FAIL_INTRODUCE);
        }
    }

    public void modifyUserAddress(PatchUserAddressReq address, Long userIdx) throws BaseException {
        try {
            userDao.modifyUserAddress(address, userIdx);
        } catch (BaseException e){
            throw e;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void modifyUserShopName(PatchUserShopnameReq shopName, Long userIdx) throws BaseException {
        try {
            userDao.modifyUserShopName(shopName, userIdx);
        } catch (BaseException e){
            throw e;
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
