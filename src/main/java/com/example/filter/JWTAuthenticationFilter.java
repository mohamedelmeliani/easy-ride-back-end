package com.example.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.utilitis.JWTUtilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager manager;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("attemptAuthentication");
        String email=request.getParameter("username");
        String password=request.getParameter("password");
        System.out.println(email);
        System.out.println(password);
        UsernamePasswordAuthenticationToken token=
                new UsernamePasswordAuthenticationToken(email,password);
        System.out.println("success attempt");
        return manager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("success");
        User user=(User) authResult.getPrincipal();
        Algorithm algorithm=Algorithm.HMAC256(JWTUtilities.SECRET);
        String access_token= JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JWTUtilities.EXPIRE_ACCESS_TOKEN))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles",user.getAuthorities().stream().map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toList()))
                .sign(algorithm);

        String refresh_token= JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+JWTUtilities.EXPIRE_REFRESH_TOKEN))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        Map<String,String>tokens=new HashMap<>();
        tokens.put("access_token",access_token);
        tokens.put("refresh_token",refresh_token);
        response.setContentType("application/json");
        System.out.println("success");
        new ObjectMapper().writeValue(response.getOutputStream(),tokens);
        System.out.println("success success");
    }
}
