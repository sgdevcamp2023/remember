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
            String token = parseBearerToken(jwtToken);
            subject = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody()
                    .getSubject();
        } catch (Exception e) {
            isTokenValid = false;
        }

        if (subject == null || subject.isEmpty()) {
            isTokenValid = false;
        }

        return isTokenValid;
    }

    public String parseBearerToken(String jwtToken) {
        if (jwtToken.startsWith("Bearer ")) {
            return jwtToken.substring(7);
        }
        return jwtToken;
    }
}