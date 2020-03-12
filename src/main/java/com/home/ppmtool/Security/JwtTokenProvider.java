package com.home.ppmtool.Security;

import com.home.ppmtool.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.home.ppmtool.Security.SecurityConstraints.EXPIRATION_TIME;
import static com.home.ppmtool.Security.SecurityConstraints.SECRET;

@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication){
        User user = (User)authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date expiryDate =  new Date(now.getTime()+EXPIRATION_TIME);
        String userId =  Long.toString(user.getId());
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",(Long.toString(user.getId())));
        claims.put("username",user.getUsername());
        claims.put("fullName",user.getFullName());

        return Jwts.builder().setSubject(userId).
                setClaims(claims).
                setIssuedAt(now).
                setExpiration(expiryDate).
                signWith(SignatureAlgorithm.HS512,SECRET).
                compact();


    }


    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch(SignatureException e){
            System.out.println("Signature invalid");
        }catch(MalformedJwtException e){
            System.out.println("Invalid jwt token");
        }catch(ExpiredJwtException e){
            System.out.println("Expired token");
        }catch(UnsupportedJwtException e){
            System.out.println("Unsupported JWT");
        }catch(IllegalArgumentException e){
            System.out.println("Illegal argument");
        }

        return false;
    }

    public Long getUserIdFromJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        String id = (String)claims.get("id");
        return Long.parseLong(id);
    }
}
