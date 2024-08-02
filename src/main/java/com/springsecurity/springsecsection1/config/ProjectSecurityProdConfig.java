package com.springsecurity.springsecsection1.config;

import com.springsecurity.springsecsection1.exceptionhandling.CustomAccessDeniedHandler;
import com.springsecurity.springsecsection1.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

//        permit all requests
//        http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());

//        deny all requests
//        http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());

//        request matchers to specify paths to blocks
        http.sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession").maximumSessions(1).maxSessionsPreventsLogin(true).expiredUrl("/expiredSession"))
                .requiresChannel(rcc -> rcc.anyRequest().requiresSecure()) //Only HTTPS connection
                .csrf(csrfConfig -> csrfConfig.disable())
                .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/notices", "/contact", "/error", "/register", "/invalidSession").permitAll());

        http.formLogin(withDefaults());

//        disable the formLogin
//        http.formLogin(formLoginConfig -> formLoginConfig.disable());

//        http.httpBasic(withDefaults());

//        invoking our custom basicAuthenticationEntryPoint

//        hbc -> httpBasicConfig
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));

//        making the authentication entry point global config
//        http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint())); //IT IS A GLOBAL CONFIG


//        CONFIGURING ACCESS DENIED HANDLER EXCEPTION AT GLOBAL LEVEL
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //    to prevent using frequently used passwords
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

//    IN MEMORY USER DETAILS MANAGER

//    we are going to save the user credentials in memory
//    @Bean
//    public UserDetailsService userDetailsService(){
//
////     IMPORTANT
////     when we do not encode our password with any password encoder
////     we need to use {noop} prefix with the password
//
//        UserDetails user = User.withUsername("user").password("{noop}SoumilBose@12345").authorities("read").build();
//        UserDetails admin = User.withUsername("admin")
//                .password("{bcrypt}$2a$12$m1k195hoJ5Znaba7ZqDCD.amr.7x3PrRd4wsY0.Zr8JotzhxM37.G") //SoumilBose@54321
//                .authorities("admin")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//
//    }

//    SINCE WE CREATED OUR OWN CUSTOM USER_DETAILS_SERVICE WE CAN COMMENT THIS

//    JDBC USER DETAILS MANAGER

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource){
//        return new JdbcUserDetailsManager(dataSource);
//    }

}

