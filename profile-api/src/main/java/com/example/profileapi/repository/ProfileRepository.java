package com.example.profileapi.repository;

import com.example.profileapi.model.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract class ProfileRepository implements CrudRepository<Profile, Long> {


}
