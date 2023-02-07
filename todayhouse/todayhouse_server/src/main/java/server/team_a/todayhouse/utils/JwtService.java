package server.team_a.todayhouse.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/** Deprecated
 * JWTUtil 사용
 */
@Deprecated
@Service
@Log4j2
public class JwtService {

    @Value("${secret.key.jwt}")
    private String JWT_SECRET_KEY;
    /*
    JWT 생성
    @param userIdx
    @return String
     */
    public String createJwt(int userIdx){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userIdx",userIdx)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365)))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY.getBytes())
                .compact();
    }

    /*
    Header에서 X-ACCESS-TOKEN 으로 JWT 추출
    @return String
     */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }

    /*
    JWT에서 userIdx 추출
    @return int
    @throws BaseException
     */
    public int getUserIdx() throws BaseException {
        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(BaseResponseStatus.EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY.getBytes())
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {
            throw new BaseException(BaseResponseStatus.INVALID_JWT);
        }

        // 3. userIdx 추출
        return claims.getBody().get("userIdx",Integer.class);  // jwt 에서 userIdx를 추출합니다.
    }

}
