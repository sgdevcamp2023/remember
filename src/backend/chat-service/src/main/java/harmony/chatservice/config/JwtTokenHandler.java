package harmony.chatservice.config;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtTokenHandler {

    @Value("${jwt.secret-key}")
    private String secretKey;

    public boolean verifyToken(String jwtToken) {
        boolean isTokenValid = true;
        String subject = null;

        try {
            subject = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken).getBody()
                    .getSubject();
        } catch (Exception e) {
            isTokenValid = false;
        }

        if (subject == null || subject.isEmpty()) {
            isTokenValid = false;
        }

        return isTokenValid;
    }
}