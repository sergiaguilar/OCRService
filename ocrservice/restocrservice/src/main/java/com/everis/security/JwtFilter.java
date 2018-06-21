package com.everis.security;

import com.everis.businesslogic.interfaces.IUserControl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    private IUserControl iUserControl;

    public JwtFilter(IUserControl iUserControl) {
        this.iUserControl=iUserControl;
    }

    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {


        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final String authHeader = request.getHeader("Authorization");


        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);

            chain.doFilter(req, res);
        } else {
            if (authHeader == null) {
                throw new ServletException("Missing or invalid Authorization header");
            }

            final String token = authHeader;
            try {
                final Claims claims = Jwts.parser().setSigningKey("everis").parseClaimsJws(token).getBody();

                String id = claims.getSubject();
                String pass = claims.getId();


                if(!iUserControl.existsUser(id)) {
                    throw new ServletException("Invalid token");
                }

                else {
                    if(!iUserControl.findUserByIdCompany(id).getPassword().equals(pass)) {
                        throw new ServletException("Invalid token");
                    }
                }


                request.setAttribute("Claims", claims);


            } catch (final SignatureException e) {
                throw new ServletException("Invalid token");
            }

            chain.doFilter(req, res);


        }
    }
}

