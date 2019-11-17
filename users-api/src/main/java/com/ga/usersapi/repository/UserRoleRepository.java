package com.ga.usersapi.repository;

import com.ga.usersapi.model.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    @Query("FROM UserRole ur where ur.name=?1")
    public UserRole getRoleByName(String name);
}
