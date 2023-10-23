package com.joago.ssloginserver.auth;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.joago.ssloginserver.model.User;
import com.joago.ssloginserver.repo.UserRepository;
import com.joago.ssloginserver.util.AuthoritiesUtil;

public class EmailPasswordAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private UserRepository repo;
  @Autowired
  private PasswordEncoder encoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String email = authentication.getName();
    String password = authentication.getCredentials().toString();

    List<User> user = repo.findByEmail(email);

    if (null != user.get(0)) {
      if (encoder.matches(password, user.get(0).getPassword())) {
        return new UsernamePasswordAuthenticationToken(email, password,
            AuthoritiesUtil.getGrantedAuthorities(user.get(0).getAuthorities()));
      } else {
        throw new BadCredentialsException("INVALID CREDENTIALS");
      }
    } else {
      throw new BadCredentialsException("USER DOESN'T EXIST");
    }
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return EmailPasswordAuthenticationProvider.class.isAssignableFrom(authentication);
  }

}