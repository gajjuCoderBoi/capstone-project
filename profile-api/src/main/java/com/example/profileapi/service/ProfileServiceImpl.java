package com.example.profileapi.service;


import com.example.profileapi.model.Profile;
import com.example.profileapi.model.User;
import com.example.profileapi.repository.ProfileRepository;
import org.apache.catalina.realm.UserDatabaseRealm;
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
     *       todo
     *      Comment for Autowired
     *
     *************************************************************************/

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProfileService profileService;

    @Autowired
    ProfileRepository profileRepository;

    /*************************************************************************
     *       todo
     *      Comment for createProfile
     *
     *************************************************************************/

    @Override
    public Profile createProfile(Profile profile, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(token.substring(7));
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        User user = restTemplate.exchange("http://users-api:5001/", HttpMethod.GET, entity, User.class).getBody();

        if (user==null){
            return null;
        }
        profile.setUserId(user.getUserId());

        return profileRepository.save(profile);
    }

    /*************************************************************************
     *       todo
     *      Comment for getProfile
     *
     *************************************************************************/

    @Override
    public Profile getProfile(Long userId) {
        return null;
    }

    /*************************************************************************
     *       todo
     *      Comment for update
     *
     *************************************************************************/
    @Override
    public Profile updateProfile(Profile profile) {
        return null;
    }
}
