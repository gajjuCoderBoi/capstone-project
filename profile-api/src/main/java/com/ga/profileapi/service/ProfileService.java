package com.ga.profileapi.service;

import com.ga.profileapi.model.Profile;

public interface ProfileService {

    /*************************************************************************
     *
     *      Profile Service has three methods that are implemented into
     *      ProfileSercviceImpl.
     *
     *************************************************************************/

    public Profile createProfile(Profile profile, String token);

    Profile getProfile(String token);

    public Profile updateProfile(Profile profile, String token);

}
