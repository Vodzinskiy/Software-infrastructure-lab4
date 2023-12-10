package vms.backend.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import vms.backend.security.UserDetailsImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtService {

    private final String jwtSecret = "HereGoesASecretKeyThatNeedsToBeMinimum64BytesLongWhenUTF8EncodedAndShouldBeKeptSafe";

    public String generateJwtToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsImpl userPrincipal) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("name", userPrincipal.getName());
            claims.put("birthDate", userPrincipal.getBirthDate().toString());
            return Jwts.builder()
                    .setSubject(userPrincipal.getUsername())
                    .setId(userPrincipal.getId().toString())
                    .setIssuedAt(new Date())
                    .addClaims(claims)
                    .setExpiration(new Date((new Date()).getTime() + 86400000))
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                    .compact();
        } else {
            throw new IllegalArgumentException("Unsupported principal type: " + principal.getClass().getName());
        }
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("name").toString();
    }

    public String getUserIDFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getId();
    }

    public UUID jwtToUUID(String jwt) {
        String userId = getUserIDFromJwtToken(jwt);
        return UUID.fromString(userId);
    }
}
