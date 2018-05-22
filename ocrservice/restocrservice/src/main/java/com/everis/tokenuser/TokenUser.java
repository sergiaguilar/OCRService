package com.everis.tokenuser;

import com.everis.autorizationauthentication.model.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class TokenUser {

    public Users getUser(String token)
    {
        Claims claims = Jwts.parser()
                .setSigningKey("everis")
                .parseClaimsJws(token)
                .getBody();
        String idCompany = claims.getSubject();
        String password = claims.getId();
        Users users = new Users();
        users.setIdCompany(idCompany);
        users.setPassword(password);
        return users;
    }

}
