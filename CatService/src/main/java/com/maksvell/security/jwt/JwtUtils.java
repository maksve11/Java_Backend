package com.maksvell.security.jwt;

import com.maksvell.dto.UserDto;
import com.maksvell.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private String jwtAccessSecret = "accessTokenSecret";

    private int jwtAccessExpirationMs = 3600000;

    public String generateJwtAccessToken(User userDetails) {
        return generateJwtAccessFromUserDetails(userDetails);
    }

    public UserDto getUserDtoFromJwtAccessToken(String token) {
        Long id = (Long) getUserValueByKey("id", token);
        String email = (String) getUserValueByKey("email", token);
        return new UserDto(id, email);
    }

    private Object getUserValueByKey(String key, String token) {
        return Jwts.parser().setSigningKey(jwtAccessSecret).parseClaimsJws(token).getBody().get(key);
    }

    private String generateJwtAccessFromUserDetails(User user) {
        return Jwts.builder()
                .claim("email", user.getEmail())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
                .setExpiration(new Date(new Date().getTime() + jwtAccessExpirationMs))
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtAccessSecret).setSigningKey(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

}
