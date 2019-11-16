package com.ga.profileapi.service;


import com.ga.profileapi.model.Profile;
import com.ga.profileapi.model.User;
import com.ga.profileapi.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Service
public class ProfileServiceImpl implements ProfileService{

    /*************************************************************************
     *
     *      Autowiring the RestTemplate, ProfileService, ProfileRepository
     *      These are the dependency for the Profile services and used in
     *      this service
     *
     *************************************************************************/

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProfileService profileService;

    @Autowired
    ProfileRepository profileRepository;


    /*************************************************************************
     *
     *      createProfile function is responsible to create the profile model
     *      and save into database. it has two parameters. First parameter is
     *      Profile that receive the data from the front-end. And the second
     *      parameter is token which validate the user authority to access the
     *      Profile API.
     *
     *      Token will be sent to User-API to validate that and User-API will
     *      return if User model if it's valid otherwise it returns null.
     *
     *
     *************************************************************************/

    @Override
    public Profile createProfile(Profile profile, String token) {
        User user = getUserFromUserAPI(token);
        if (user==null){
            return null;
        }
        profile.setUserId(user.getUserId());
        profile.setUsername(user.getEmail());
        Profile savedProfile = profileRepository.save(profile);
        savedProfile.setUsername(user.getEmail());
        return savedProfile;
    }

    /*************************************************************************
     *
     *      getProfile is responsible to get the profile from the database
     *      of a User using userId, and userId will be get from the User
     *      object that is received from User-Api by sending the token.
     *
     *************************************************************************/

    @Override
    public Profile getProfile(String token) {
        User user = getUserFromUserAPI(token);
        if (user==null){
            return null;
        }

        Profile savedProfile = profileRepository.getProfileByUserId(user.getUserId());
        savedProfile.setUsername(user.getEmail());
        return savedProfile;
    }

    /*************************************************************************
     *
     *      updateProfile function is responsible to create the profile model
     *      and save into database. it has two parameters. First parameter is
     *      Profile that receive the data from the front-end. And the second
     *      parameter is token which validate the user authority to access the
     *      Profile API.
     *
     *      Token will be sent to User-API to validate that and User-API will
     *      return if User model if it's valid otherwise it returns null.
     *
     *      This function also validate that the profile should exist into
     *      the database otherwise return null.
     *
     *      After receive the User profile will be updated and save into the
     *      database.
     *
     *************************************************************************/
    @Override
    public Profile updateProfile(Profile profile, String token) {
        User user = getUserFromUserAPI(token);
        if (user==null){
            return null;
        }

        Profile savedProfile = profileRepository.getProfileByUserId(user.getUserId());
        if (savedProfile==null){
            return null;
        }
        savedProfile.setAdditionalEmail(profile.getAdditionalEmail());
        savedProfile.setAddress(profile.getAddress());
        savedProfile.setPhone(profile.getPhone());

        profileRepository.save(savedProfile);
        savedProfile.setUsername(user.getEmail());
        return profileRepository.save(savedProfile);
    }
    /*************************************************************************
     *
     *      getUserFromUserAPI is responsible to send the token to the User-API
     *      and return the response, the response is either User model or NULL.
     *
     *************************************************************************/

    private User getUserFromUserAPI(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token.substring(7));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        return restTemplate.exchange("http://users-api:5001/", HttpMethod.GET, entity, User.class).getBody();
    }
}
