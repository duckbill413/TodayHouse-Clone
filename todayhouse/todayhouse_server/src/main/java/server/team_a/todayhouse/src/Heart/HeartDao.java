package server.team_a.todayhouse.src.Heart;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import server.team_a.todayhouse.src.Heart.model.GetMyHeartsRes;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HeartDao {

    private JdbcTemplate jdbcTemplate;



    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int addPlaceHeart(int userIdx, int placeIdx) {

        String query1 = "SELECT EXISTS(select  * from heart where users_idx = ? and place_idx = ?)  ";

        boolean isExists = jdbcTemplate.queryForObject(query1, boolean.class, new Object[] {userIdx, placeIdx});


        String query2 = "update heart set status = true where users_idx = ? and place_idx = ?  ";

        String query3 = "INSERT INTO heart (created_at, updated_at, deleted, status, users_idx, place_idx) VALUES  " +
                "(now(), now(), false, true, ?, ?)  ";


        if (isExists) {
           return jdbcTemplate.update(query2, new Object[] {userIdx, placeIdx});

        } else {

            return jdbcTemplate.update(query3, new Object[] {userIdx, placeIdx});

        }


    }

    public int removePlaceHeart(int userIdx, int placeIdx) {
        String query1 = "SELECT EXISTS(select  * from heart where users_idx = ? and place_idx = ?)  ";

        boolean isExists = jdbcTemplate.queryForObject(query1, boolean.class, new Object[] {userIdx, placeIdx});

        String query2 = "update heart set status = false where users_idx = ? and place_idx = ?  ";

        String query3 = "INSERT INTO heart (created_at, updated_at, deleted, status, users_idx, place_idx) VALUES  " +
                "(now(), now(), false, false, ?, ?)  ";


        if (isExists) {
            return jdbcTemplate.update(query2, new Object[] {userIdx, placeIdx});

        } else {
            return jdbcTemplate.update(query3, new Object[] {userIdx, placeIdx});

        }
    }

    public List<GetMyHeartsRes> getMyHearts(int userIdx) {

        List<GetMyHeartsRes> result = new ArrayList<>();

        String query1 = "select place_idx from heart  " +
                "INNER JOIN user u on heart.users_idx = u.idx  " +
                "where u.idx = ? and status = true";

        List<Integer> placeIds = jdbcTemplate.query(query1, (rs, rsNum) -> new Integer(rs.getInt("place_idx")), userIdx);

        String query2 = "select image_url from place  " +
                "INNER JOIN place_image pi on place.idx = pi.place_idx  " +
                "where place_idx = ? limit 1  ";

        for (Integer placeIdx : placeIds) {
            GetMyHeartsRes temp = jdbcTemplate.queryForObject(query2, (rs, rsNum) ->
                    new GetMyHeartsRes(placeIdx, rs.getString("image_url")), placeIdx);

            result.add(temp);
        }

        return result;

    }

    public boolean isPlaceIdxExists(int placeIdx) {

        String query = "select Exists(select * from place where idx = ?)  ";

        return jdbcTemplate.queryForObject(query, boolean.class, placeIdx);
    }




}
