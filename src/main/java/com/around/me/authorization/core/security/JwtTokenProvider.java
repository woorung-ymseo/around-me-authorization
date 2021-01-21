package com.around.me.authorization.core.security;

import com.around.me.authorization.api.v1.authorization.dto.UserParamDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 *
 * JWT를 생성하고 검증하는 컴포넌트
 * JWT에는 토큰 만료 시간이나 회원 권한 정보등을 저장할 수 있음.
 *
 */
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

//    private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    @Value("${jwtTokenLogin}")
    private String secretKey;

    private Key key;

    // 토큰 유효시간 30분
    public final static long TOKEN_VALID_TIME = 30 * 60 * 1000L;
    public final static long REFRESH_TOKEN_VALID_TIME = 2 * 24 * 60 * 60 * 1000L;

    public final static String ACCESS_TOKEN_NAME = "accessToken";
    public final static String REFRESH_TOKEN_NAME = "refreshToken";

    private final UserDetailsService userDetailService;

    // 객체 초기화. secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    // JWT 토큰 생성
    public String createToken(String userPk, long expireTime) {
        Claims claims = Jwts.claims().setSubject(userPk); // JWT payload 에 저장되는 정보단위
        claims.put("userName", userPk); // 정보는 key / value 쌍으로 저장된다.
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 정보저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + expireTime)) // set Expire Time
                .signWith(key, SignatureAlgorithm.HS256) // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
//                .signWith(SignatureAlgorithm., secretKey)
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
    }

    public String generateToken(UserParamDto user) {
        return createToken(user.getUserEmail(), TOKEN_VALID_TIME);
    }

    public String generateRefreshToken(UserParamDto user) {
        return createToken(user.getUserEmail() + "_refresh", REFRESH_TOKEN_VALID_TIME);
    }

    // Request 의 Header에서 token 값을 가져옵니다. "X-AUTH_TOKEN" : "TOKEN값"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-Auth-Token");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
