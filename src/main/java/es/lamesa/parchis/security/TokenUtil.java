package es.lamesa.parchis.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class TokenUtil {

    private static final String SECRET_KEY = "mySecretKey";
    private static final long EXPIRATION_TIME = 60 * 20 * 1000; // 20 minutos

    public static String generateToken(Long id, String email) {
        return Jwts.builder()
                .setId(id.toString())
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static boolean validateToken(String token, Long id) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            Long tokenId = Long.parseLong(claims.getId());
            return tokenId.equals(id) && !claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
}
