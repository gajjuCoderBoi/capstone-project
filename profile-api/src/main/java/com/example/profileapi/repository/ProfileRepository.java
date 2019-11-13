package com.example.profileapi.repository;

import com.example.profileapi.model.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {
/*************************************************************************
 *       todo
 *      Comment for Profile Repository
 *
 *************************************************************************/

}
