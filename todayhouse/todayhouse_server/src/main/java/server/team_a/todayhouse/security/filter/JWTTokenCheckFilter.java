package server.team_a.todayhouse.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import server.team_a.todayhouse.config.BaseResponseStatus;
import server.team_a.todayhouse.security.CustomUsersDetailService;
import server.team_a.todayhouse.security.exception.AccessTokenException;
import server.team_a.todayhouse.utils.JWTUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Log4j2
@RequiredArgsConstructor
public class JWTTokenCheckFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final CustomUsersDetailService customUsersDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if(!path.startsWith("/api")){
            filterChain.doFilter(request, response);
            return;
        }
        try {
            Map<String, Object> claim = validateAccessToken(request);
            Authentication authentication = getAuthentication(Long.valueOf((Integer) claim.get("userIdx")));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (AccessTokenException e){
            e.sendResponseError(response);
        }
    }

    private Map<String, Object> validateAccessToken(HttpServletRequest request) throws AccessTokenException {
        String headerStr = request.getHeader("Authorization");
        if (headerStr == null)
            headerStr = request.getHeader("X-ACCESS-TOKEN");
        if (headerStr == null) { // 방문자
            Map<String, Object> values = new HashMap<>();
            values.put("userIdx", 9999999);
            return values;
        }

//        if(headerStr == null  || headerStr.length() < 8){
//            throw new AccessTokenException(BaseResponseStatus.JWT_TOKEN_ISNULL);
//        }

        //Bearer 생략
        String tokenType = headerStr.substring(0,6);
        String tokenStr;
        if(tokenType.equalsIgnoreCase("Bearer") == false){
//            throw new AccessTokenException(BaseResponseStatus.BAD_TYPE_JWT);
            tokenStr = headerStr;
        } else {
            tokenStr = headerStr.substring(7);
        }


        try{
            Map<String, Object> values = jwtUtil.validateJwtToken(tokenStr);

            return values;
        }catch(MalformedJwtException malformedJwtException){
            throw new AccessTokenException(BaseResponseStatus.MALFORMED_JWT);
        }catch(SignatureException signatureException){
            throw new AccessTokenException(BaseResponseStatus.BAD_SIGNATURE_JWT);
        }catch(ExpiredJwtException expiredJwtException){
            throw new AccessTokenException(BaseResponseStatus.EXPIRED_JWT);
        }
    }

    private Authentication getAuthentication(Long userIdx){
        UserDetails userDetails = customUsersDetailService.loadUserByUserIdx(userIdx);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
