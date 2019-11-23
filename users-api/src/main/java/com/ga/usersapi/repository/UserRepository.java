package com.ga.usersapi.repository;

import com.ga.usersapi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.net.UnknownServiceException;
import io.swagger.annotations.ApiOperation;

@Repository
@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("FROM User u where u.email = ?1")
    public User getUserByUsername(String username);

}
