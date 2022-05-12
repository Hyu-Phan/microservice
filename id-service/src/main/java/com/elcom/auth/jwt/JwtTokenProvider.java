package com.elcom.auth.jwt;

import com.elcom.auth.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwtExpiration}")
    private int jwtExp;

    public String createToken(CustomUserDetails userDetails) {
        Date now = new Date();
        Date expDay = new Date(now.getTime() + jwtExp);
        return Jwts.builder()
                .setSubject(userDetails.getUser().getUsername())
                .setIssuedAt(now)
                .setExpiration(expDay)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String getUuidFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (Exception e) {

        }
        return false;
    }
}
