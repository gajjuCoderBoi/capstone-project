package com.ga.profileapi.service;

import com.ga.profileapi.bean.User;
import com.ga.profileapi.exception.ProfileNotFoundException;
import com.ga.profileapi.exception.TokenException;
import com.ga.profileapi.messagequeue.Sender;
import com.ga.profileapi.model.Profile;
import com.ga.profileapi.repository.ProfileRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private Sender sender;

    @InjectMocks
    private ProfileServiceImpl profileService;

    private Profile profile;

    private User dummyUser;

    @Before
    public void initDummies(){
        profile = new Profile();
        profile.setAdditionalEmail("additional email");
        profile.setMobile("123");
        profile.setAddress("xyz");
        profile.setProfileId(1L);

        dummyUser = new User();
        dummyUser.setEmail("batman@email.com");
        dummyUser.setUsername("batman");
        dummyUser.setUserId(1L);

    }

    @Test
    public void createProfile_Profile_Success() throws ProfileNotFoundException, TokenException {
        when(sender.getUserFromUserAPI(anyString())).thenReturn(dummyUser);
        when(profileRepository.getProfileByUserId(anyLong())).thenReturn(null);
        when(profileRepository.save(any())).thenReturn(profile);

        Profile actual = profileService.createProfile(profile, "xyz");

        assertEquals(profile.getProfileId(), actual.getProfileId());

    }

    @Test(expected = TokenException.class)
    public void createProfile_TokenException_Error() throws ProfileNotFoundException, TokenException {
        when(sender.getUserFromUserAPI(anyString())).thenReturn(null);

        Profile actual = profileService.createProfile(profile, "xyz");

        assertEquals(profile.getProfileId(), actual.getProfileId());

    }

    @Test
    public void getProfile_Profile_Success() throws ProfileNotFoundException, TokenException {
        when(sender.getUserFromUserAPI(anyString())).thenReturn(dummyUser);
        when(profileRepository.getProfileByUserId(anyLong())).thenReturn(profile);

        Profile actual = profileService.getProfile("xyz");

        assertEquals(profile.getProfileId(), actual.getProfileId());

    }

    @Test(expected = TokenException.class)
    public void getProfile_TokenException_Error() throws ProfileNotFoundException, TokenException {
        when(sender.getUserFromUserAPI(anyString())).thenReturn(null);
        Profile actual = profileService.getProfile("xyz");

        assertEquals(profile.getProfileId(), actual.getProfileId());

    }

    @Test(expected = ProfileNotFoundException.class)
    public void getProfile_ProfileNotFoundException_Error() throws ProfileNotFoundException, TokenException {
        when(sender.getUserFromUserAPI(anyString())).thenReturn(dummyUser);
        when(profileRepository.getProfileByUserId(anyLong())).thenReturn(null);

        Profile actual = profileService.getProfile("xyz");

        assertEquals(profile.getProfileId(), actual.getProfileId());

    }

    @Test
    public void updateProfile_Profile_Success() throws ProfileNotFoundException, TokenException {
        when(sender.getUserFromUserAPI(anyString())).thenReturn(dummyUser);
        when(profileRepository.getProfileByUserId(anyLong())).thenReturn(profile);
        when(profileRepository.save(any())).thenReturn(profile);

        Profile actual = profileService.updateProfile(profile, "xyz");

        assertEquals(profile.getProfileId(), actual.getProfileId());
    }

    @Test(expected = TokenException.class)
    public void updateProfile_TokenException_Error() throws ProfileNotFoundException, TokenException {
        when(sender.getUserFromUserAPI(anyString())).thenReturn(null);

        Profile actual = profileService.updateProfile(profile, "xyz");

        assertEquals(profile.getProfileId(), actual.getProfileId());

    }

    @Test(expected = ProfileNotFoundException.class)
    public void updateProfile_ProfileNotFoundException_Error() throws ProfileNotFoundException, TokenException {
        when(sender.getUserFromUserAPI(anyString())).thenReturn(dummyUser);
        when(profileRepository.getProfileByUserId(anyLong())).thenReturn(null);

        Profile actual = profileService.updateProfile(profile, "xyz");

        assertEquals(profile.getProfileId(), actual.getProfileId());

    }

}