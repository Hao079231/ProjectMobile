package vn.ute.mobile.project.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String generateToken(String username, String userId) {
    return Jwts.builder()
        .setSubject(username)
        .claim("userId", userId)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expiration))
        .signWith(getSigningKey())
        .compact();
  }

  public Claims extractAllClaims(String token) {
    JwtParser parser = Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build();
    return parser.parseClaimsJws(token).getBody();
  }

  public String extractUsername(String token) {
    return getClaims(token).getSubject();
  }

  public String extractUserId(String token) {
    return extractAllClaims(token).get("userId").toString();
  }

  private Claims getClaims(String token) {
    JwtParser parser = Jwts.parserBuilder()
        .setSigningKey(getSigningKey())
        .build();
    return parser.parseClaimsJws(token).getBody();
  }

  public boolean validateToken(String token, String username) {
    String tokenUsername = extractUsername(token);
    return (tokenUsername.equals(username) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return getClaims(token).getExpiration().before(new Date());
  }
}