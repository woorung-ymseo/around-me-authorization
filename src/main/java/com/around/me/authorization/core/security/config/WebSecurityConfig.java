package com.around.me.authorization.core.security.config;

import com.around.me.authorization.api.v1.authorization.util.CookieUtil;
import com.around.me.authorization.api.v1.authorization.util.RedisUtil;
import com.around.me.authorization.core.security.JwtTokenProvider;
import com.around.me.authorization.core.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 *  Spring Security를 사용하기 위해서는 Spring Security Filter Chain 을 사용한다는 것을 명시
 *
 */
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;
    private final JwtTokenProvider jwtTokenProvider;

    // 암호화에 필요한 PasswordEncoder 를 Bean 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // authenticationManager 를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제
                .csrf().disable() // csrf 보안 토큰 disable처리
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 사용 안함
                .and()
                .authorizeRequests() // 요청에 대한 사용권한 체크
                .antMatchers("/admin/**").hasRole("ADMIN") //  "/admin/**", "/user/**" 형식의 URL로 들어오는 요청에 대해 인증을 요구
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/**").permitAll() // 그 외 나머지 요청은 누구나 접근 가능
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(userDetailsService, jwtTokenProvider, cookieUtil, redisUtil)
                        , UsernamePasswordAuthenticationFilter.class); // JwtAuthenticationFilter 를 UsernamePasswordAuthenticationFilter 전에 넣는다.


    }
}
