package server.team_a.todayhouse.src.users;


import org.springframework.security.crypto.password.PasswordEncoder;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.src.users.model.*;
import server.team_a.todayhouse.utils.JWTUtil;
import server.team_a.todayhouse.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.team_a.todayhouse.utils.SHA256;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static server.team_a.todayhouse.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private final JWTUtil jwtUtil;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService, PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.userDao = userDao;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public List<GetUserRes> getUsers() throws BaseException{
        try{
            List<GetUserRes> getUserRes = userDao.getUsers();
            return getUserRes;
        }
        catch (Exception exception) {
            logger.error("App - getUserRes Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserRes> getUsersByEmail(String email) throws BaseException {
        try {
            List<GetUserRes> getUsersRes = userDao.getUsersByEmail(email);
            return getUsersRes;
        } catch (Exception exception) {
            logger.error("App - getUsersByEmail Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public GetUserRes getUser(int userIdx) throws BaseException {
        try {
            GetUserRes getUserRes = userDao.getUser(userIdx);
            return getUserRes;
        } catch (Exception exception) {
            logger.error("App - getUser Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkEmail(String email) throws BaseException{
        try{
            return userDao.checkEmail(email);
        } catch (Exception exception){
            logger.error("App - checkEmail Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkNickName(String nickname) throws BaseException {
        try{
            return userDao.checkNickName(nickname);
        } catch (Exception exception){
            logger.error("App - checkEmail Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostLoginRes emailLogin(PostLoginReq postLoginReq) throws BaseException {
        User user;

        try {
            user = userDao.getPwd(postLoginReq);
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_LOGIN);
        }


        String encryptPwd;
        try {
            encryptPwd = passwordEncoder.encode(postLoginReq.getPassword());
        } catch (Exception exception) {
            logger.error("App - logIn Provider Encrypt Error", exception);
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }


        if(passwordEncoder.matches(postLoginReq.getPassword(), user.getPassword())){
            int userIdx = (int) user.getIdx();

            Map map = new HashMap<String, Object>();
            map.put("userIdx", userIdx);
            System.out.println("hi");
            String jwt = jwtUtil.generateJwtToken(map, 100);
            System.out.println("hello");
            return new PostLoginRes(userIdx,jwt);
        }
        else{
            throw new BaseException(FAILED_TO_LOGIN);
        }

    }

    public GetMyPageRes getMyPage(int userIdx) throws BaseException {

        try {
            GetMyPageRes result = userDao.getMyPage(userIdx);

            return result;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public void changeAuth (Long userIdx, String role, String shopName) throws BaseException {
        userDao.changeAuth(userIdx, role, shopName);
    }
}
