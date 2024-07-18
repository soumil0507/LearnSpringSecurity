package com.springsecurity.springsecsection1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

//        permit all requests
//        http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());

//        deny all requests
//        http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());

//        request matchers to specify paths to blocks
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/notices", "/contact", "/error").permitAll());

        http.formLogin(withDefaults());

//        disable the formLogin
//        http.formLogin(formLoginConfig -> formLoginConfig.disable());

        http.httpBasic(withDefaults());
        return http.build();
    }
}
