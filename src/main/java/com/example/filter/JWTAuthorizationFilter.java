package com.example.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.utilitis.JWTUtilities;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/refreshToken")||request.getServletPath().equals("/swagger-ui")){
            filterChain.doFilter(request,response);
        }
        else{
            String token=request.getHeader(JWTUtilities.AUTH_HEADER);
            if(token!=null&& token.startsWith(JWTUtilities.PREFIX)){
                try{
                    String jwt=token.substring(JWTUtilities.PREFIX.length());
                    Algorithm algorithm=Algorithm.HMAC256(JWTUtilities.SECRET);
                    JWTVerifier jwtVerifier= JWT.require(algorithm).build();
                    jwtVerifier.verify(jwt);
                    DecodedJWT decodedJWT=jwtVerifier.verify(jwt);
                    String username=decodedJWT.getSubject();
                    String[] roles=decodedJWT.getClaim("roles").asArray(String.class);
                     Collection<GrantedAuthority>authorities=new ArrayList<>();
                    for (String role:roles){
                        authorities.add(new SimpleGrantedAuthority(role));
                    }
                    UsernamePasswordAuthenticationToken authenticationToken=
                            new UsernamePasswordAuthenticationToken(username,null,authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request,response);
                }catch(Exception e){
                    response.setHeader("Error_Message",e.getMessage());
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
            }
            else {
                filterChain.doFilter(request,response);
            }
        }
    }
}
