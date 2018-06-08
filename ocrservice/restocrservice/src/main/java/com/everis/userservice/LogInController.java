package com.everis.userservice;

import com.everis.autorizationauthentication.model.Users;
import com.everis.businesslogic.interfaces.IUserControl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.util.Calendar;
import java.util.Date;

@RestController
public class LogInController {

    @Autowired
    private IUserControl iUserControl;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestBody Users login) throws ServletException {
        String jwtToken = "";

        if (login.getIdCompany() == null || login.getPassword() == null) {
            throw new ServletException("Please fill in username and password");
        }

        String idCompany = login.getIdCompany();
        String password = login.getPassword();

        Users users = iUserControl.findUserByIdCompany(idCompany);

        if (users == null) {
            throw new ServletException("Users ID not found.");
        }

        String pwd = users.getPassword();

        if (!password.equals(pwd)) {
            throw new ServletException("Invalid login. Please check your ID and password.");
        }

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 30);
        Date expireDate = calendar.getTime();

        jwtToken = Jwts.builder().setSubject(idCompany).setId(password).setIssuedAt(date).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, "everis").compact();

        return jwtToken;
    }
}