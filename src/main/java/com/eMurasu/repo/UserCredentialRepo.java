package com.eMurasu.repo;

import com.eMurasu.model.UserCredential;
import org.springframework.data.repository.CrudRepository;

public interface UserCredentialRepo extends CrudRepository<UserCredential, String> {
    UserCredential findByUsername(String username);
}
