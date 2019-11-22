package com.ga.profileapi.service;

import com.ga.profileapi.exception.ProfileNotFoundException;
import com.ga.profileapi.exception.TokenException;
import com.ga.profileapi.model.Profile;

public interface ProfileService {

    /*************************************************************************
     *
     *      Profile Service has three methods that are implemented into
     *      ProfileSercviceImpl.
     *
     *************************************************************************/

    public Profile createProfile(Profile profile, String token) throws TokenException, ProfileNotFoundException;

    Profile getProfile(String token) throws TokenException, ProfileNotFoundException;

    public Profile updateProfile(Profile profile, String token) throws ProfileNotFoundException, TokenException;

}
