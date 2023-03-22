package com.chubov.SpringTelegramBot.JWT;

import com.chubov.SpringTelegramBot.services.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@PropertySource("classpath:application.properties")
public class JwtTokenProvider {

    //  Base64 secretKey

    private final static String SECRET_KEY = "oUdzcb5tXY8+k6LZfV6pv2ZJlZGjF/bXiQ1tiPg+Wz0=";

    private final static Long EXPIRATION_TIME = 3600000L;

    private static final String AUTHORITIES_KEY = "authorities";

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public JwtTokenProvider(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    //  Don't use cause, Token generating on client side.
    public String generateToken(Authentication authentication) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        User user = (User) authentication.getPrincipal();
        Claims claims = Jwts.claims().setSubject(user.getUsername());
        claims.put(AUTHORITIES_KEY, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(expiryDate).signWith(key).compact();
    }

//    public Authentication getAuthentication(String token) {
//        UserDetails userDetails = this.userDetailsService.loadUserByUsername(String.valueOf(getTelegramIdFromToken(token)));
//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String croppedToken = resolveToken(token);
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(croppedToken).getBody();
        String username = claims.getSubject();
        return (username.equals(userDetails.getUsername()) && !claims.getExpiration().before(new Date()));
    }

    //  Parse JWT and extract Telegram ID claim
    public static Long getTelegramIdFromToken(String token) {
        System.out.println(token);
        token = resolveToken(token);
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        Long telegramId = Long.parseLong(claims.getSubject());
        return telegramId;
    }

    public static String resolveToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7).trim();
        }
        return null;
    }

    //
    public UserDetails getUserDetailsFromToken(String token) {
        token = resolveToken(token);
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token).getBody();

        String subject = claims.getSubject();
        List<String> authorities = (List<String>) claims.get("authorities");

        UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
        return new User(userDetails.getUsername(), userDetails.getPassword(), authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    }

    public boolean isValidAndAdmin(String token) {
        UserDetails userDetails = getUserDetailsFromToken(token);
        if (validateToken(token, userDetails) && userDetails.getAuthorities().stream().toList().get(0).toString().equals("ROLE_ADMIN")) {
            return true;
        }
        return false;
    }
}
