package com.oorjacafe.oorjapay.security;

import com.oorjacafe.oorjapay.service.CustomUserDetailsService;
import com.oorjacafe.oorjapay.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;


    public JwtFilter( JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService=jwtService;
        this.userDetailsService=userDetailsService;


    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader= request.getHeader("Authorization");

        //check if header exists and starts with Bearer

        String token = null;
        String username = null;

        //1. Extract token
        if(authHeader !=null && authHeader.startsWith("Bearer ")){
             token =authHeader.substring(7);
             username=jwtService.extractUsername(token);
        }

        //2. Validation and set authentication

        if (username != null &&
                SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.validateToken(token, userDetails.getUsername())) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        //3. Continue filter chain
        filterChain.doFilter(request, response);
    }
}
