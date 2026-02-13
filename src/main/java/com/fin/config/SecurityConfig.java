package com.fin.config;

import com.fin.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${security.rememberMe.key}")
    private String rememberMeKey;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/**","/index").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
//                        .successHandler((request, response, authentication) -> {
//                            response.setContentType("application/json");
//                            response.setStatus(HttpServletResponse.SC_OK);
//                            response.setCharacterEncoding("UTF-8");
//
//                            response.getWriter().write("""
//                    {
//                        "status": true,
//                        "msg": "Login successful"
//                    }
//                """);
//                        })
//
//                        // ⭐ FAILURE → JSON
//                        .failureHandler((request, response, exception) -> {
//                            response.setContentType("application/json");
//                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                            response.setCharacterEncoding("UTF-8");
//
//                            response.getWriter().write("""
//                    {
//                        "status": false,
//                        "msg": "Invalid username or password"
//                    }
//                """);
//                        })
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/index", true)
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .key(rememberMeKey)
                        .useSecureCookie(true)
                        .tokenValiditySeconds(2 * 24 * 60 * 60)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
