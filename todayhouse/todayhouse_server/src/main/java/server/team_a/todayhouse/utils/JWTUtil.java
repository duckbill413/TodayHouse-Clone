package server.team_a.todayhouse.utils;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import server.team_a.todayhouse.config.BaseException;
import server.team_a.todayhouse.config.BaseResponseStatus;
import server.team_a.todayhouse.repository.UsersRepository;
import server.team_a.todayhouse.security.CustomUsersDetailService;
import server.team_a.todayhouse.security.dto.UsersSecurityDTO;
import server.team_a.todayhouse.src.users.model.Users;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Log4j2
public class JWTUtil {
    @Value("${secret.key.jwt}")
    private String JWT_SECRET_KEY;
    private final CustomUsersDetailService customUsersDetailService;

    public String generateJwtToken(Map<String, Object> valueMap, int days){
        Map<String, Object> headers = new HashMap<>();
        // header
        headers.put("typ", "jwt");
        headers.put("alg", "HS256");
//        Map map = new HashMap<String, Object>();
//        map.put("userIdx", userIdx);
        // payload
        Map<String, Object> payloads = new HashMap<>();
        payloads.putAll(valueMap);

        // token lifetime
        int time = (60*24) * days;

        String jwtStr = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(time).toInstant()))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY.getBytes())
                .compact();
        return jwtStr;
    }
    public Map<String, Object> validateJwtToken(String token) throws JwtException {
        Map<String, Object> claim = Jwts.parser()
                .setSigningKey(JWT_SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claim;
    }
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("X-ACCESS-TOKEN");
    }
}
