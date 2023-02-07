package server.team_a.todayhouse.src.review;


import server.team_a.todayhouse.src.review.model.GetReviewsRes;
import server.team_a.todayhouse.src.review.model.PostCreateReviewReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetReviewsRes> getReviews(int userIdx){

        String query = "select r.idx  " +
                "from review r  " +
                "INNER JOIN user u on u.idx = r.users_idx  " +
                "WHERE u.idx = ?  ";

        List<Integer> reviewIds = this.jdbcTemplate.query(query,
                (rs,rowNum)-> new Integer(rs.getInt("idx")), userIdx);



        String query2 = "select u.nickname, u.profile_img, r.product_idx, r.idx, r.updated_at, p.title, r.body, r.grade from review r  " +
                "INNER JOIN product p on r.product_idx = p.idx  " +
                "INNER JOIN user u on r.users_idx = u.idx  " +
                "where r.idx = ?  ";

        List<GetReviewsRes> result = new ArrayList<>();


        for (Integer reviewId : reviewIds) {

            GetReviewsRes temp = this.jdbcTemplate.queryForObject(query2,
                    (rs, rsNum) -> new GetReviewsRes(
                            rs.getString("nickname"),
                            rs.getString("profile_img"),
                            rs.getInt("product_idx"),
                            rs.getInt("idx"),
                            rs.getString("updated_at"),
                            rs.getString("title"),
                            rs.getString("body"),
                            rs.getFloat("grade"),
                            new ArrayList<>()

                    ), reviewId);


            String query3 = "select image_url from review_image ri  " +
                    "INNER  JOIN review r on ri.review_idx = r.idx  " +
                    "where r.idx = ?";


            temp.setImages(this.jdbcTemplate.query(query3, (rs, rsNum) -> new String(rs.getString("image_url")), reviewId));

            result.add(temp);
        }

        return result;
    }

    public List<GetReviewsRes> getProductReviews(int productIdx) {

        String query1 = "select r.idx from review r  " +
                "INNER JOIN product p on r.product_idx = p.idx  " +
                "where p.idx = ?  ";

        List<Integer> reviewIds = this.jdbcTemplate.query(query1,
                (rs,rowNum)-> new Integer(rs.getInt("idx")), productIdx);


        String query2 = "select u.nickname, u.profile_img, r.product_idx, r.idx, r.updated_at, p.title, r.body, r.grade from review r  " +
                "INNER JOIN product p on r.product_idx = p.idx  " +
                "INNER JOIN user u on r.users_idx = u.idx  " +
                "where r.idx = ?  ";

        List<GetReviewsRes> result = new ArrayList<>();


        for (Integer reviewId : reviewIds) {

            GetReviewsRes temp = this.jdbcTemplate.queryForObject(query2,
                    (rs, rsNum) -> new GetReviewsRes(
                            rs.getString("nickname"),
                            rs.getString("profile_img"),
                            rs.getInt("product_idx"),
                            rs.getInt("idx"),
                            rs.getString("updated_at"),
                            rs.getString("title"),
                            rs.getString("body"),
                            rs.getFloat("grade"),
                            new ArrayList<>()

                    ), reviewId);


            String query3 = "select image_url from review_image ri  " +
                    "INNER  JOIN review r on ri.review_idx = r.idx  " +
                    "where r.idx = ?";


            temp.setImages(this.jdbcTemplate.query(query3, (rs, rsNum) -> new String(rs.getString("image_url")), reviewId));

            result.add(temp);
        }

        return result;

    }

    public int createReview(int userIdx, int productIdx, PostCreateReviewReq req) {

        String query = "INSERT INTO review (created_at, updated_at, body, deleted, grade, title, product_idx, users_idx)\n" +
                "VALUES  (now(), now(), ?, false, ?, \"제목\", ?, ?)";

        Object[] modifyUserNameParams = new Object[]{req.getBody(), req.getGrade(), productIdx, userIdx};

        int result = jdbcTemplate.update(query, modifyUserNameParams);

        return result;

    }

    public int modifyReview(int userIdx, int reviewIdx, PostCreateReviewReq req) {
        int result = 0;

        String query1 = "update review set body = ? where review.idx = ?  ";

        String query2 = "update review set grade = ? where review.idx = ?  ";


        if (req.getBody() != null) {
            Object[] modifyParams = new Object[]{req.getBody(), reviewIdx};

            result = jdbcTemplate.update(query1, modifyParams);
        }

        if (req.getGrade() != null) {
            Object[] modifyParams = new Object[]{req.getGrade(), reviewIdx};

            result = jdbcTemplate.update(query2, modifyParams);

        }

        return result;


    }


    public boolean isReviewExists(int userIdx, int productIdx) {

        String query = "select EXISTS(select * from review where users_idx = ? and product_idx = ?)  ";


        return jdbcTemplate.queryForObject(query, boolean.class, new Object[] {userIdx, productIdx});
    }

    public boolean isProductExists(int productIdx) {
        String query = "select EXISTS(select * from product where idx = ?)  ";

        return jdbcTemplate.queryForObject(query, boolean.class, productIdx);
    }

    public boolean checkReviewAndUserId(int reviewIdx, int userIdx) {
        String query = "select EXISTS(select * from review where idx = ? and users_idx = ?)  ";

        return jdbcTemplate.queryForObject(query, boolean.class, new Object[] {reviewIdx, userIdx});
    }



}
