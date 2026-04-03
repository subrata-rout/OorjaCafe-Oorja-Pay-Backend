package com.oorjacafe.oorjapay.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authHeder= request.getHeader("Authorization");

        //check if heder exists nd strts with Bearer
        if(authHeder !=null && authHeder.startsWith("Bearer ")){
            String token =authHeder.substring(7);
             try{
                 String email=jwtUtil.extractEmail(token);
                 System.out.println("Valid token for: "+ email);
             }catch (Exception e){
                 System.out.println("Invalid token");
                 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                 return;
             }

        }
        filterChain.doFilter(request,response);

    }
}
