package com.appexam.security.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private Long expiration;


    private Key getSigninKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Générer un token
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", userDetails.getAuthorities()
                .stream().map(
                        GrantedAuthority::getAuthority
                ).toList());
        return createToken(claims, userDetails.getUsername());

    }

    public String createToken(Map<String, Object> claims, String subject){
        return  Jwts.builder()
                .setClaims(claims)
                .setIssuer("Api backend")
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigninKey())
                .compact();

    }

    public <T> T extractClaim(String token , Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extraire le username du token
    public String extractUsername(String token){
        return  extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    // Extraire tous les claims
    public Claims extractAllClaims(String token){
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigninKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (JwtException e){
            throw new JwtException("Invalid token");
        }
    }


    // Vérifier si le token est expiré
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Valider le token
    public Boolean validateToken(String token ,UserDetails userDetails) {
        final  String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }
}
