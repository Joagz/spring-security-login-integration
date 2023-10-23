package com.joago.ssloginserver.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.joago.ssloginserver.model.User;
import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
  List<User> findByEmail(String email);
}
