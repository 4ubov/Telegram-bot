package com.chubov.SpringTelegramBot.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    //  Base64 secretKey
    private static final String SECRET_KEY = "oUdzcb5tXY8+k6LZfV6pv2ZJlZGjF/bXiQ1tiPg+Wz0=";

    //  Don't use cause, Token generating on client side.
    public static String generateToken(String telegramId) {
        Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expiration = new Date(nowMillis + 1200000); // 20 minute
        return Jwts.builder().setSubject(telegramId).setIssuedAt(now).setExpiration(expiration).signWith(SignatureAlgorithm.HS256, key).compact();
    }

    //  Parse JWT and extract Telegram ID claim
    public static Long getTelegramIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        Long telegramId = (Long) claims.get("telegramId");
        return telegramId;
    }


}
