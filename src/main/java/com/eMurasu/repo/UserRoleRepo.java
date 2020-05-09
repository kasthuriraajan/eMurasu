package com.eMurasu.repo;

import com.eMurasu.model.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepo extends CrudRepository<UserRole, Integer> {
    UserRole findById(int userRoleId);
}
