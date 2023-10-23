package com.joago.ssloginserver.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {

    // ! CONFIGURE REQUESTS !

    http.authorizeHttpRequests(
        request -> request

            .requestMatchers("/protected/*").permitAll()
            .anyRequest().permitAll())

        .formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults());

    // ! CONFIGURE CORS !
    http.csrf(csrf -> csrf.disable());
    http.cors(cors -> cors.configurationSource(

        new CorsConfigurationSource() {

          @Override
          @Nullable
          public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
            configuration.setAllowCredentials(true);
            configuration.setMaxAge(3600L);

            return configuration;

          }

        }));

    return http.build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}
