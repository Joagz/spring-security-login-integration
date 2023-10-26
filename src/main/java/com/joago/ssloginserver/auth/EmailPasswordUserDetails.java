package com.joago.ssloginserver.auth;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.joago.ssloginserver.model.User;
import com.joago.ssloginserver.repo.UserRepository;
import com.joago.ssloginserver.util.AuthoritiesUtil;

@Service
public class EmailPasswordUserDetails implements UserDetailsService {

  @Autowired
  private UserRepository repo;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    List<User> found = repo.findByEmail(username);
    User user = found.get(0);

    if (null != user) {
      List<GrantedAuthority> authorities = AuthoritiesUtil.getGrantedAuthorities(user.getAuthorities());
      return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
    } else {
      throw new UsernameNotFoundException("USER NOT FOUND WITH USERNAME '" + username + "'");
    }

  }

}
