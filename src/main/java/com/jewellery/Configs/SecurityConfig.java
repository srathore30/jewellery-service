package com.jewellery.Configs;

import com.jewellery.AuthUtils.JWTAuthenticationEntryPoint;
import com.jewellery.AuthUtils.JwtAuthenticationFilter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Setter
@Configuration
public class SecurityConfig {
    @Autowired
    private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.
                        requestMatchers("/swagger-ui/**","/v3/api-docs/swagger-config","/v3/api-docs/**","/swagger-ui/index.html").permitAll().
                        requestMatchers("/auth/login").permitAll().
                        requestMatchers("/auth/create").permitAll().
                        requestMatchers("/auth/createFamilyMember").permitAll().
                        requestMatchers("/auth/resetPassword").permitAll().
                        requestMatchers("/auth/validateToken").permitAll().
                        anyRequest().authenticated())
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)) // if any exception come
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // nothing to save on server
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
//    http://api.smartsalonbot.com/auth-service/swagger-ui/index.html


}
