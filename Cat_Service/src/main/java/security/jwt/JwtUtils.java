package security.jwt;

import com.example.lab4server.dto.UserDto;
import com.example.lab4server.entities.UserEntity;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${maksvell.app.jwtAccessSecret}")
    private String jwtAccessSecret;

    @Value("${maksvell.app.jwtAccessExpirationMs}")
    private int jwtAccessExpirationMs;

    public String generateJwtAccessToken(UserEntity userDetails) {
        return generateJwtAccessFromUserDetails(userDetails);
    }

    public UserDto getUserDtoFromJwtAccessToken(String token) {
        String id = (String) getUserValueByKey("id", token);
        String email = (String) getUserValueByKey("email", token);
        return new UserDto(id, email);
    }

    private Object getUserValueByKey(String key, String token) {
        return Jwts.parser().setSigningKey(jwtAccessSecret).parseClaimsJws(token).getBody().get(key);
    }

    private String generateJwtAccessFromUserDetails(UserEntity user) {
        return Jwts.builder()
                .claim("email", user.getEmail())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
                .setExpiration(new Date(new Date().getTime() + jwtAccessExpirationMs))
                .compact();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtAccessSecret).parseClaimsJws(authToken);
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
