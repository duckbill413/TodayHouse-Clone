package server.team_a.todayhouse.utils;

import groovy.util.logging.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;


@SpringBootTest
class JWTUtilTest {
    @Autowired
    private JWTUtil jwtUtil;
    @Test
    void jwtTest(){
        String jwtStr = jwtUtil.generateJwtToken(Map.of(
                "email", "duckbill@gmail.com", "password", "duckbill"), 1);
        System.out.println(jwtStr);
        Map<String, Object> claim = jwtUtil.validateJwtToken(jwtStr);
        System.out.println("EMAIL: " +claim.get("userIdx"));
        System.out.println("PASSWORD: " + claim.get("password"));
    }

}