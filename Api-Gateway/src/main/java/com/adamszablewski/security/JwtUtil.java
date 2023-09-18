package com.adamszablewski.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.security.SignatureException;

@Component
public class JwtUtil {

    public static final String JWT_SECRET = "462D4A614E635266556A586E3272357538782F413F4428472B4B6250655368566B5970337336763979244226452948404D635166546A576E5A7134743777217A";

    public String getUsernameFromJWT(String token){
        System.out.println("get username from token called ");
        System.out.println(token);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
        System.out.println(claims.getSubject());
        return claims.getSubject();
    }
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT has expired");
        } catch (MalformedJwtException e) {
            throw new RuntimeException("JWT is malformed or has an invalid signature");
        } catch (Exception e) {
            throw new RuntimeException("JWT validation failed: " + e.getMessage());
        }
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}