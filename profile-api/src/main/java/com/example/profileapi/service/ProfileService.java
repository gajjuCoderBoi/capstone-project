package com.example.profileapi.service;

import com.example.profileapi.model.Profile;

public interface ProfileService {

    public Profile createProfile(Profile profile);

    public Profile getProfile(Long userId);

    public Profile updateProfile(Profile profile);

}
