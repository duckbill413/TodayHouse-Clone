package server.team_a.todayhouse.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import server.team_a.todayhouse.security.CustomOAuth2UsersService;
import server.team_a.todayhouse.security.filter.CustomUsersLoginFilter;
import server.team_a.todayhouse.security.filter.JWTTokenCheckFilter;
import server.team_a.todayhouse.security.handler.CustomSocialLoginSuccessHandler;
import server.team_a.todayhouse.security.CustomUsersDetailService;
import server.team_a.todayhouse.security.handler.UsersLoginFailHandler;
import server.team_a.todayhouse.security.handler.UsersLoginSuccessHandler;
import server.team_a.todayhouse.utils.JWTUtil;

import javax.sql.DataSource;
import java.util.Arrays;

@Log4j2
@Configuration
@RequiredArgsConstructor
//@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig {
    @Value("${secret.key.remember-me}")
    private String REMEMBER_ME_KEY;
    private final DataSource dataSource;
    private final CustomAuthDetails customAuthDetails;
    private final CustomUsersDetailService customUsersDetailService;
    private final JWTUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // AuthenticationManager 설정
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(customUsersDetailService)
                .passwordEncoder(passwordEncoder());
        // Get AuthenticationManager
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        // AuthenticationManager 부착
        http.authenticationManager(authenticationManager);
        // Login Filter
        CustomUsersLoginFilter customUsersLoginFilter = new CustomUsersLoginFilter("/api/users/log-in");
        customUsersLoginFilter.setAuthenticationManager(authenticationManager);

        // success handler
        UsersLoginSuccessHandler usersLoginSuccessHandler = new UsersLoginSuccessHandler(jwtUtil);
        customUsersLoginFilter.setAuthenticationSuccessHandler(usersLoginSuccessHandler);
        // fail handler
        UsersLoginFailHandler usersLoginFailHandler = new UsersLoginFailHandler();
        customUsersLoginFilter.setAuthenticationFailureHandler(usersLoginFailHandler);
        // Filter 위치 조정
        http.addFilterBefore(customUsersLoginFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(
                jwtTokenCheckFilter(jwtUtil),
                UsernamePasswordAuthenticationFilter.class
        );

        // INFO: 커스텀 로그인 페이지
        // csrf 설정
//        http.headers().disable(); // 보상상 bad
//        http.headers().frameOptions().sameOrigin();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        /* FEAT: JWT만 사용
        // request 권한 설정
        http.authorizeRequests(request -> request
                .antMatchers("/").permitAll()
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
        );
        // 로그인 설정
        http.formLogin().loginPage("/login").permitAll()
                .usernameParameter("email")
                .defaultSuccessUrl("/", false)
                .failureUrl("/login-error")
                .authenticationDetailsSource(customAuthDetails);
        // Session disable
        // 로그아웃 설정
        http.logout().logoutSuccessUrl("/");
        // Exception 설정
        http.exceptionHandling().accessDeniedPage("/access-denied");
        // remember-me 설정
        http.rememberMe()
                .key(REMEMBER_ME_KEY)
                .tokenRepository(persistentTokenRepository())
                .userDetailsService(customUsersDetailService)
                .tokenValiditySeconds(60 * 60 * 24 * 30);
         */
        http.oauth2Login().successHandler(authenticationSuccessHandler());

        http.cors(httpSecurityCorsConfigurer -> {
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource());
        });

        return http.build();
    }
    @Bean
    RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//        roleHierarchy.setHierarchy("ADMIN > SELLER > USER > VISITOR");
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_SELLER > ROLE_USER > ROLE_VISITOR");
        return roleHierarchy;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
//                .antMatchers("/swagger-ui/**", "/css/**", "/js/**", "/images/**", "/error", "/api/**")
                .antMatchers("/swagger-ui/**", "/css/**", "/js/**", "/images/**", "/error")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomSocialLoginSuccessHandler(passwordEncoder(), jwtUtil);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        repo.setDataSource(dataSource);
        return repo;
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private JWTTokenCheckFilter jwtTokenCheckFilter(JWTUtil jwtUtil) {
        return new JWTTokenCheckFilter(jwtUtil, customUsersDetailService);
    }
}
