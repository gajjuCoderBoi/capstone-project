package com.ga.profileapi.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ga.profileapi.exception.ProfileNotFoundException;
import com.ga.profileapi.exception.TokenException;
import com.ga.profileapi.messagequeue.Sender;
import com.ga.profileapi.model.Profile;
import com.ga.profileapi.bean.User;
import com.ga.profileapi.repository.ProfileRepository;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


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

    @Autowired
    Sender sender;


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
    public Profile createProfile(Profile profile, String token) throws TokenException, ProfileNotFoundException {
        User user = sender.getUserFromUserAPI(token);
        if (user==null){
            throw new TokenException("Invalid Token.");
        }
        if(profileRepository.getProfileByUserId(user.getUserId())!=null){
            return updateProfile(profile, token);
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
    public Profile getProfile(String token) throws TokenException, ProfileNotFoundException {
        User user = sender.getUserFromUserAPI(token);
        if (user==null){
            throw new TokenException("Invalid Token.");
        }

        Profile savedProfile = profileRepository.getProfileByUserId(user.getUserId());
        if (savedProfile==null) throw new ProfileNotFoundException("Profile does not exist");
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
    public Profile updateProfile(Profile profile, String token) throws ProfileNotFoundException, TokenException {
        User user = sender.getUserFromUserAPI(token);
        if (user==null){
            throw new TokenException("Invalid Token.");
        }

        Profile savedProfile = profileRepository.getProfileByUserId(user.getUserId());
        if (savedProfile==null){
            throw new ProfileNotFoundException("Profile does not exist.");
        }
        savedProfile.setAdditionalEmail(profile.getAdditionalEmail());
        savedProfile.setAddress(profile.getAddress());
        savedProfile.setMobile(profile.getMobile());

        profileRepository.save(savedProfile);
        savedProfile.setUsername(user.getEmail());
        return profileRepository.save(savedProfile);
    }

}
