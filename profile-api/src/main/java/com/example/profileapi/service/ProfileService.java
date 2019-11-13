package com.example.profileapi.service;

import com.example.profileapi.model.Profile;

public interface ProfileService {

    /*************************************************************************
     *       todo
     *      Comment for ProfileService
     *
     *************************************************************************/

    public Profile createProfile(Profile profile, String token);

    public Profile getProfile(Long userId);

    public Profile updateProfile(Profile profile);

}
