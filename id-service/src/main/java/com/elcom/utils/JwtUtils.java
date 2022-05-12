package com.elcom.utils;

import com.elcom.auth.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtUtils {

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
                .signWith(SignatureAlgorithm.ES512, jwtSecret)
                .compact();
    }

    public String getUuidFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
