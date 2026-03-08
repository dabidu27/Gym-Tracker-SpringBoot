package com.gymtracker.demo.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.util.Date;

@Component
public class JwtService {

    @Value("${jwt.secret}") //extracting value from application.properties
    private String SECRET;
    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + this.expiration))
                .signWith(Keys.hmacShaKeyFor(this.SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {

        return Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(this.SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Boolean isExpired(String token){

        Date expiration = Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(this.SECRET.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    public Boolean isTokenValid(String token, UserDetails userDetails){

        String username = this.extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }


}
