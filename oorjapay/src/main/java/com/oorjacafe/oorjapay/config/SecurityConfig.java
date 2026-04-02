package com.oorjacafe.oorjapay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    //Password Encoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
     return new BCryptPasswordEncoder();
    }

    //Disable default security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth-> auth.anyRequest()
                        .permitAll()
                );
        return http.build();
    }

}
