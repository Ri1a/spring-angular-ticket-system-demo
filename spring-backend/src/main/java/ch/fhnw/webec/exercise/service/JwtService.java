package ch.fhnw.webec.exercise.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtService {

    private final SecretKey signingKey;
    private final Duration ttl;
    private final String issuer;
    private final String audience;
    private final Clock clock;

    public JwtService(
            @Value("${security.jwt.secret-base64}") String secretBase64,
            @Value("${security.jwt.ttl:PT168H}") Duration ttl,
            @Value("${security.jwt.issuer:ticketing-system-demo}") String issuer,
            @Value("${security.jwt.audience:ticketing-api}") String audience
    ) {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretBase64));
        this.ttl = ttl;
        this.issuer = issuer;
        this.audience = audience;
        this.clock = Clock.systemUTC();
    }

    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now(clock);

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authorities(userDetails));

        return Jwts.builder()
                .issuer(issuer)
                .audience().add(audience).and()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(ttl)))
                .id(UUID.randomUUID().toString())
                .claims(claims)
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            Claims claims = parseClaims(token);

            boolean subjectOk = Objects.equals(claims.getSubject(), userDetails.getUsername());
            boolean notExpired = claims.getExpiration().after(Date.from(Instant.now(clock)));
            boolean issuerOk = Objects.equals(claims.getIssuer(), issuer);
            boolean audienceOk = claims.getAudience() == null || claims.getAudience().contains(audience);

            return subjectOk && notExpired && issuerOk && audienceOk;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private static List<String> authorities(UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}