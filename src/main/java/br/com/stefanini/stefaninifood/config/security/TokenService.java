package br.com.stefanini.stefaninifood.config.security;

import br.com.stefanini.stefaninifood.model.Consumer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${stefaninifood.jwt.expiration}")
    private String expDuration;

    @Value("${stefaninifood.jwt.secret}")
    private String secret;

    public String generateToken(Authentication authentication){
        Consumer logged = (Consumer) authentication.getPrincipal();
        Date today = new Date();
        Date expDate = new Date(today.getTime() + Long.parseLong(expDuration));
        return Jwts.builder()
                .setIssuer("StefaniniFood API REST")
                .setSubject(logged.getUsername())
                .setIssuedAt(today)
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserId(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
