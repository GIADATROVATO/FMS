package dev.store.services;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
@Service
public class JwtService {
private final Key key= Keys.hmacShaKeyFor("my-super-secret-jwt-key-that-is-long-enough-256bits".getBytes());
	
	public String generateToken(String username, String role ) {
		return Jwts.builder().setSubject(username).claim("role",role).setIssuedAt(new Date()).setExpiration(
				new Date(System.currentTimeMillis()+3600000)).signWith(key, SignatureAlgorithm.HS256).compact();
	
	}
	public Claims extractClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
		
	}
}
