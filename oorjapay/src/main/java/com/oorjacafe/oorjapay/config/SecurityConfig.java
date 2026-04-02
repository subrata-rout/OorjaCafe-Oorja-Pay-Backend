package com.oorjacafe.oorjapay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig {
    //Password Encoder
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
     return new BCryptPasswordEncoder();
    }

}
