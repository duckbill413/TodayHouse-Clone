package server.team_a.todayhouse.src.users;


import org.springframework.transaction.annotation.Transactional;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponseStatus;
import server.team_a.todayhouse.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import server.team_a.todayhouse.src.base.embedded.Address;
import server.team_a.todayhouse.src.users.model.*;
import server.team_a.todayhouse.utils.JWTUtil;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;
    private final UsersRepository usersRepository;
    private final JWTUtil jwtUtil;

    public UserDao(UsersRepository usersRepository, JWTUtil jwtUtil) {
        this.usersRepository = usersRepository;
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select * from UserInfo";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password"))
                );
    }

    public List<GetUserRes> getUsersByEmail(String email){
        String getUsersByEmailQuery = "select * from UserInfo where email =?";
        String getUsersByEmailParams = email;
        return this.jdbcTemplate.query(getUsersByEmailQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password")),
                getUsersByEmailParams);
    }

    public GetUserRes getUser(int userIdx){
        String getUserQuery = "select * from UserInfo where userIdx = ?";
        int getUserParams = userIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userIdx"),
                        rs.getString("userName"),
                        rs.getString("ID"),
                        rs.getString("Email"),
                        rs.getString("password")),
                getUserParams);
    }

    public PostUserRes createUser(PostUserReq postUserReq){
        String createUserQuery = "INSERT INTO user (created_at, updated_at, deleted, email, nickname, password, social, profile_img) " +
                "VALUE (now(), now(), false, ?, ?, ?, false, \"https://rc12-todayhouse-s3.s3.ap-northeast-2.amazonaws.com/profile/default_profile.webp\")  ";
        Object[] createUserParams = new Object[]{postUserReq.getEmail(), postUserReq.getNickname(), postUserReq.getPassword()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        int userIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
        PostUserRes postUserRes = new PostUserRes();
        postUserRes.setUserIdx(userIdx);

        Map map = new HashMap<String, Object>();
        map.put("userIdx", userIdx);

        postUserRes.setJwt(jwtUtil.generateJwtToken(map, 100));

        String createUserRoleQuery = "INSERT INTO users_role_set (users_idx, role_set) VALUES (?, 'USER')";
        this.jdbcTemplate.update(createUserRoleQuery, userIdx);
        postUserRes.setUserRole(Arrays.asList(UserRole.USER));
        return postUserRes;
    }

    public int checkEmail(String email){
        String checkEmailQuery = "select exists(select email from user where email = ?)";
        String checkEmailParams = email;
        return this.jdbcTemplate.queryForObject(checkEmailQuery,
                int.class,
                checkEmailParams);

    }

    public int checkNickName(String nickname) {
        String query = "select EXISTS(select * from user where nickname = ?)  ";

        return this.jdbcTemplate.queryForObject(query,
                int.class,
                nickname);
    }

    public int modifyUserNickName(String nickName, int userIdx){
        String modifyUserNameQuery = "update user set nickname = ? where idx = ? ";
        Object[] modifyUserNameParams = new Object[]{nickName, userIdx};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public int modifyUserPassWord(String password, int userIdx){
        String modifyUserNameQuery = "update user set password = ? where idx = ? ";
        Object[] modifyUserNameParams = new Object[]{password, userIdx};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public int modifyUserIntroduce(String introduce, int userIdx){
        String modifyUserNameQuery = "update user set introduce = ? where idx = ? ";
        Object[] modifyUserNameParams = new Object[]{introduce, userIdx};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public User getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select idx, email, password, nickname, address, phone_number, profile_img, introduce, deleted, social from user where email = ?";
        String getPwdParams = postLoginReq.getEmail();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("idx"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nickname"),
                        rs.getString("address"),
                        rs.getString("phone_number"),
                        rs.getString("profile_img"),
                        rs.getString("introduce"),
                        rs.getBoolean("deleted"),
                        rs.getBoolean("social")
                ),
                getPwdParams
                );

    }

    public GetMyPageRes getMyPage(int userIdx) {
        String query = "select nickname, profile_img, introduce from user where idx = ?  ";

        GetMyPageRes result =  jdbcTemplate.queryForObject(query, (rs, rsNum) ->
                new GetMyPageRes(
                        rs.getString("nickname"),
                        rs.getString("profile_img"),
                        rs.getString("introduce")), userIdx);



        String query2 = "select Count(*) from heart  " +
                "INNER  JOIN user u on heart.users_idx = u.idx  " +
                "where u.idx = ? and heart.status = true ";

        String query3 = "select Count(*) from scrap  " +
                "INNER  JOIN user u on scrap.users_idx = u.idx  " +
                "where u.idx = ? and scrap.status = true ";

        String query4 = "select COUNT(*) from review where users_idx = ?  ";

        result.setHeartCount(jdbcTemplate.queryForObject(query2, int.class, userIdx));

        result.setScrapCount(jdbcTemplate.queryForObject(query3, int.class, userIdx));

        result.setReviewCount(jdbcTemplate.queryForObject(query4, int.class, userIdx));

        return result;

    }
    @Transactional
    public void modifyUserAddress(PatchUserAddressReq address, Long userIdx) throws BaseException {
        Users users = usersRepository.findById(userIdx).orElseThrow(
                () -> new BaseException(BaseResponseStatus.FAILED_TO_FIND_USER)
        );
        users.setAddress(new Address(address.getAddress(), address.getZipcode()));
        usersRepository.save(users);
    }
    @Transactional
    public void changeAuth(Long idx, String role, String shopName) throws BaseException {
        UserRole userRole = Enum.valueOf(UserRole.class, role);
        if ((userRole == UserRole.SELLER || userRole == UserRole.ADMIN) && shopName == null){
            throw new BaseException(BaseResponseStatus.SHOPNAME_CANNOT_NULL);
        }
        Users users = usersRepository.findById(idx).orElseThrow(()->
                new BaseException(BaseResponseStatus.FAILED_TO_FIND_USER));
        users.getRoleSet().add(userRole);
        usersRepository.save(users);
    }

    @Transactional
    public void modifyUserShopName(PatchUserShopnameReq shopName, Long userIdx) throws BaseException {
        Users users = usersRepository.findById(userIdx).orElseThrow(() ->
                new BaseException(BaseResponseStatus.FAILED_TO_FIND_USER));
        users.setShopName(shopName.getShopName());
        usersRepository.save(users);
    }
}
