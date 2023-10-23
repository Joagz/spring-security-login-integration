package com.joago.ssloginserver.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.joago.ssloginserver.model.Authority;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthoritiesUtil {

  public List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
    List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

    for (Authority authority : authorities) {
      grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
    }

    return grantedAuthorities;
  }

}
