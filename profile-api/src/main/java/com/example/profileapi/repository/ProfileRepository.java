package com.example.profileapi.repository;

import com.example.profileapi.model.Profile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends CrudRepository<Profile, Long> {
/*************************************************************************
 *
 *      Profile Repository has only one custom method name getProfileByUserId
 *      that will get the Profile by the userId.
 *
 *************************************************************************/

@Query("FROM Profile p where p.userId=?1")
public Profile getProfileByUserId(Long userId);

}
