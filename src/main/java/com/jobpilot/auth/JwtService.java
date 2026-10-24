package com.jobpilot.auth;

import java.sql.Date;
import java.time.Instant;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    private final SecretKey key;
    private final long ttlseconds;
    
    public JwtService(@Value("${app.jwt.secret}") String secret, @Value ("${app.jwt.ttl-seconds:3600}") long ttlseconds){
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.ttlseconds=ttlseconds;
    }

    public String generateToken(String email){
        Instant now=Instant.now();
        return Jwts.builder().subject(email).issuedAt(Date.from(now))
        .expiration(Date.from(now.plusSeconds(ttlseconds)))
        .signWith(key)
        .compact();
    }

    public String extractEmail(String token){
        return Jwts.parser().verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
    }
}
