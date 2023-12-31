package com.joago.ssloginserver.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.joago.ssloginserver.model.Authority;
import com.joago.ssloginserver.model.User;
import com.joago.ssloginserver.repo.AuthorityRepository;
import com.joago.ssloginserver.repo.UserRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class UserAndAuthorityApi {

  @Autowired
  private UserRepository repo;

  @Autowired
  private AuthorityRepository authorityRepository;

  @Autowired
  private PasswordEncoder encoder;

  @GetMapping
  public String home() {
    return "SUCCESSFULLY CONNECTED TO SPRING SECURITY LOGIN TEST";
  }

  @GetMapping("/protected/user")
  public List<User> findAll() {
    return repo.findAll();
  }

  @GetMapping("/profile")
  public User getProfile(Authentication authentication) {
    return repo.findByEmail(authentication.getName()).get(0);
  }

  @PostMapping("/protected/authority")
  public ResponseEntity<?> createAuthority(@RequestBody Authority entity) {
    if (null != entity) {
      return ResponseEntity.ok().body(authorityRepository.save(entity));
    } else {
      return ResponseEntity.badRequest().body("COULD NOT CREATE AUTHORITY");
    }
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody User entity) {
    if (null != entity) {
      entity.setPassword(encoder.encode(entity.getPassword()));
      Set<Authority> authorities = new HashSet<Authority>();
      authorities.add(authorityRepository.findByName("USER").get(0));
      authorities.add(authorityRepository.findByName("ADMIN").get(0));
      entity.setAuthorities(authorities);
      return ResponseEntity.ok().body(repo.save(entity));
    } else {
      return ResponseEntity.badRequest().body("COULD NOT CREATE USER");
    }
  }

}
