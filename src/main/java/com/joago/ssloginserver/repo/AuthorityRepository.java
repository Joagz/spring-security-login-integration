package com.joago.ssloginserver.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.joago.ssloginserver.model.Authority;
import java.util.List;

public interface AuthorityRepository extends MongoRepository<Authority, String> {

  List<Authority> findByName(String name);

}
