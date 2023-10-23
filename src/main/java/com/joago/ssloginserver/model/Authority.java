package com.joago.ssloginserver.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(value = "authorities")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Authority {

  @Id
  private String id;
  private String name;
  
}
