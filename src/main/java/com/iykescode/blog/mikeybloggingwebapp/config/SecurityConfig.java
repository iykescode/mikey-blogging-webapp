package com.iykescode.blog.mikeybloggingwebapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/auth/**").anonymous()
                                .requestMatchers("/user/**").authenticated()
                                .requestMatchers("/blog/view/**").authenticated()
                                .requestMatchers("/**").permitAll()
                )
                .formLogin(login ->
                        login
                                .loginPage("/auth/login")
                                .defaultSuccessUrl("/user/")
                                .failureUrl("/auth/login?error=true")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/auth/login?logout=true")
                                .invalidateHttpSession(true)
                                .permitAll()
                )
                .headers(header -> header.frameOptions(Customizer.withDefaults()).disable())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
