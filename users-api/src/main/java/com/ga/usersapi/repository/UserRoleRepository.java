package com.ga.usersapi.repository;

import com.ga.usersapi.model.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
/*************************************************************************
 * The UserRoleRepository handles all DB calls related to posts
 * it implements the CrudRepository class
 * A special Query was set up to find userroles by name
 *
 *************************************************************************/


public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    @Query("FROM UserRole ur where ur.name=?1")
    UserRole getRoleByName(String name);
}
