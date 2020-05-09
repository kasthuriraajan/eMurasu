package com.eMurasu.repo;

import com.eMurasu.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {
    User findById(int userId);
    User findByUsername(String username);
}
