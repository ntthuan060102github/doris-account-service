package doris.dorisaccountservice.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import doris.dorisaccountservice.dto.UserDetailsImp;

@Component
public class JwtTokenProvider {
    private final String JWT_SECRET = "1a7-92A-8y2-559-dr8-12H-446-7DQ-04c-5a8-5fA";

    public String generateToken(UserDetailsImp userDetails, Integer ttl)
    {
        Date now = new Date();

        return Jwts.builder()
                    .setSubject(userDetails.getUser().getId().toString())
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + ttl))
                    .signWith(SignatureAlgorithm.HS256, this.JWT_SECRET)
                    .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                            .setSigningKey(JWT_SECRET)
                            .parseClaimsJws(token)
                            .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
