package server.team_a.todayhouse.src.place;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import server.team_a.todayhouse.src.place.model.GetMyPlacesRes;
import server.team_a.todayhouse.src.place.model.GetPlaceDetailRes;
import server.team_a.todayhouse.src.place.model.GetPlacesRes;
import server.team_a.todayhouse.src.place.model.PatchPlaceReq;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlaceDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


   public List<GetPlacesRes> getPlaces(int userIdx) {
        String query1 = "select idx from place  ";

        List<Integer> placeIds = jdbcTemplate.query(query1, (rs, rsNum) -> new Integer(rs.getInt("idx")));

        List<GetPlacesRes> result = new ArrayList<>();

        String query2 = "select image_url, message from place_image where place_idx = ? limit 1;  ";

        String query3 = "select exists(select * from scrap  " +
                "INNER JOIN place p on scrap.place_idx = p.idx  " +
                "INNER JOIN user u on p.users_idx = u.idx  " +
                "where place_idx = ? and u.idx = ? and scrap.status = true)  ";


        for (Integer placeIdx : placeIds) {

            GetPlacesRes temp = jdbcTemplate.queryForObject(query2, (rs, rsNum) -> new GetPlacesRes(
                    placeIdx,
                    rs.getString("image_url"),
                    rs.getString("message")
            ), placeIdx);

            if (userIdx != -1) {
                Object[] params = {placeIdx, userIdx};
                int t = jdbcTemplate.queryForObject(query3, int.class, params);

                if (t == 1) {
                    temp.setScraped(true);
                }
            }

            result.add(temp);

        }

        return result;


   }

   public GetPlaceDetailRes getPlaceDetail(int placeIdx, int userIdx) {
        String query1 = "select  u.nickname, u.profile_img, pi.updated_at, pi.image_url, pi.message from place  " +
                "INNER JOIN place_image pi on place.idx = pi.place_idx  " +
                "INNER JOIN user u on place.users_idx = u.idx  " +
                "where place_idx = ? limit 1  ";

        GetPlaceDetailRes result = jdbcTemplate.queryForObject(query1, (rs, rsNum) -> new GetPlaceDetailRes(
                rs.getString("nickname"),
                rs.getString("profile_img"),
                rs.getString("updated_at"),
                rs.getString("image_url"),
                rs.getString("message")
        ), placeIdx);

        String query2 = "select COUNT(*) from heart where place_idx = ? and heart.status = true  ";

        String query3 = "select COUNT(*) from scrap where place_idx = ? and scrap.status = true  ";

        result.setHeartCount(jdbcTemplate.queryForObject(query2, int.class, placeIdx));

        result.setScrapCount(jdbcTemplate.queryForObject(query3, int.class, placeIdx));


        String query4 = "select exists(select * from scrap  " +
                "INNER JOIN place p on scrap.place_idx = p.idx  " +
                "INNER JOIN user u on p.users_idx = u.idx  " +
                "where place_idx = ? and u.idx = ? and scrap.status = true)  ";


        String query5 = "select exists(select * from heart  " +
                "INNER JOIN place p on heart.place_idx = p.idx  " +
                "INNER JOIN user u on p.users_idx = u.idx  " +
                "where place_idx = ? and u.idx = ? and heart.status = true)  ";



        if (userIdx != -1) {
            Object[] params = {placeIdx, userIdx};
            boolean t = jdbcTemplate.queryForObject(query4, boolean.class, params);

            if (t) {
                result.setScraped(true);
            }

            t = jdbcTemplate.queryForObject(query5, boolean.class, params);

            if (t) {
                result.setHearted(true);
            }


        }

        return result;
   }

   public int modifyPlace(int userIdx, int placeIdx, PatchPlaceReq req) {

        String query1 = "select EXISTS(select * from place  " +
                "where users_idx = ? and idx = ?) ";

        Object[] params = {userIdx, placeIdx};

        boolean isExist = jdbcTemplate.queryForObject(query1, boolean.class, params);

        String query = "select place_image.idx from place_image  " +
                "INNER JOIN place p on place_image.place_idx = p.idx  " +
                "where p.idx = ? limit 1  ";

        int placeImageIdx = jdbcTemplate.queryForObject(query, int.class, placeIdx);


        String query2 = "update place_image set message = ? WHERE idx = ?";

        Object[] params2 = {req.getMessage(), placeImageIdx};


        if (isExist) {
            int result = jdbcTemplate.update(query2, params2);
            return result;

        } else {
            return 0;
        }

   }

   public boolean doesUserHaveAuthorityPlace(int userIdx, int placeIdx) {
       String query1 = "select EXISTS(select * from place  " +
               "where users_idx = ? and idx = ?) ";

       Object[] params = {userIdx, placeIdx};

       boolean isExist = jdbcTemplate.queryForObject(query1, boolean.class, params);

       return isExist;
   }

   public List<GetMyPlacesRes> getMyPlaces(int userIdx) {

        List<GetMyPlacesRes> result = new ArrayList<>();

        String query1 = "select idx from place where users_idx = ?  ";

        List<Integer> placeIds = jdbcTemplate.query(query1, (rs, rsNum) -> new Integer(rs.getInt("idx")), userIdx);

        String query2 = "select space, image_url from place_image  " +
                "INNER JOIN place p on place_image.place_idx = p.idx  " +
                "where place_idx = ? limit 1  ";

        for (Integer placeIdx : placeIds) {

            GetMyPlacesRes temp = jdbcTemplate.queryForObject(query2, (rs, rsNum) -> new GetMyPlacesRes(
                    placeIdx, rs.getString("space")  ,rs.getString("image_url")
            ), placeIdx);

            result.add(temp);


        }

        return result;

   }

   public boolean isPlaceIdxExists(int placeIdx) {

        String query = "select Exists(select * from place where idx = ?)  ";

        return jdbcTemplate.queryForObject(query, boolean.class, placeIdx);
   }



}
