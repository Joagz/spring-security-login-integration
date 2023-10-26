package com.joago.ssloginserver.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.joago.ssloginserver.auth.EmailPasswordAuthenticationProvider;
import com.joago.ssloginserver.filter.LoginFilter;
import com.joago.ssloginserver.repo.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

  @Bean
  AuthenticationManager myAuthenticationManager(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    EmailPasswordAuthenticationProvider provider = new EmailPasswordAuthenticationProvider(userRepository,
        passwordEncoder);
    return provider::authenticate;
  }

  @Bean
  public SecurityFilterChain configure(HttpSecurity http, AuthenticationManager authenticationManager)
      throws Exception {

    http.addFilterAt(new LoginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);

    http.securityContext(sc -> sc.requireExplicitSave(false));
    http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

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

    // ! CONFIGURE REQUESTS !
    http.authorizeHttpRequests(
        request -> request
            .requestMatchers("/").authenticated()
            .requestMatchers("/protected/*").authenticated()
            .requestMatchers("/login").permitAll().anyRequest()
            .permitAll())

        .formLogin(Customizer.withDefaults()).formLogin(login -> login.disable())
        .httpBasic(Customizer.withDefaults());

    return http.build();

  }

  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}
