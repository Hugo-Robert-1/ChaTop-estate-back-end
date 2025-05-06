package com.chatop.estate.services;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.chatop.estate.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class JWTService {

	@Value("${security.jwt.expiration-time}")
    private long expirationTime;
	
	private PrivateKey getPrivateKey() throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get("src/main/resources/keys/private.pem"));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }
	
	private PublicKey getPublicKey() throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get("src/main/resources/keys/public.pem"));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

	public String generateToken(String username) {
        try {
            Instant now = Instant.now();
            return Jwts.builder()
                    .claim("sub", username)
                    .claim("iat", Date.from(now))
                    .claim("exp", Date.from(now.plusMillis(expirationTime)))
                    .signWith(getPrivateKey(), io.jsonwebtoken.SignatureAlgorithm.RS256)
                    .compact();
        } catch (Exception e) {
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

	public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractEmail(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
	public boolean isTokenValid(String token, User user) {
        String email = extractEmail(token);
        return email != null && email.equals(user.getEmail()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
	
	public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }
	
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }
	
	private Claims extractAllClaims(String token) {
        try {
			return Jwts.parser()
			        .verifyWith(getPublicKey())
			        .build()
			        .parseSignedClaims(token)
			        .getPayload();
		} catch (Exception e) {
			throw new RuntimeException("Error extracting claims from JWT token", e);
		}
    }
}