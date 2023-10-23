package com.joago.ssloginserver.model;

import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(value = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class User {

  @Id
  private String user_id;
  private String username;
  private String email;

  @Builder.Default()
  private Date create_dt = new Date();

  @JsonProperty(access = Access.WRITE_ONLY)
  private String password;

  @DBRef
  private Set<Authority> authorities;

}
