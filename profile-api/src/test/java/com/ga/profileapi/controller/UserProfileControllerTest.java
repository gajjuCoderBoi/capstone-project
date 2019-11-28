package com.ga.profileapi.controller;

import com.ga.profileapi.model.Profile;
import com.ga.profileapi.service.ProfileServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserProfileControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private UserProfileController userProfileController;

    @Mock
    ProfileServiceImpl profileService;

    private String dummyToken = "xyz";

    private Profile dummyProfile;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileController).build();
    }

    @Before
    public void initDummies() {
        dummyProfile = new Profile();
        dummyProfile.setAdditionalEmail("email@email.com");
        dummyProfile.setMobile("123");
        dummyProfile.setAddress("xyz");
        dummyProfile.setProfileId(1L);
    }


    @Test
    public void getProfile_Profile_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/")
                .header(HttpHeaders.AUTHORIZATION, dummyToken)
                .contentType(MediaType.APPLICATION_JSON);

        when(profileService.getProfile(anyString())).thenReturn(dummyProfile);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void getProfile_ProfileNotFound_Error() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andReturn();

    }

    @Test
    public void createProfile_Profile_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/")
                .header(HttpHeaders.AUTHORIZATION, "xyz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProfileInJson(dummyProfile.getAdditionalEmail(), dummyProfile.getMobile(), dummyProfile.getAddress()));
        System.out.println(createProfileInJson(dummyProfile.getAdditionalEmail(), dummyProfile.getMobile(), dummyProfile.getAddress()));
        when(profileService.createProfile(any(), anyString())).thenReturn(dummyProfile);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void createProfile_Unauthorized_Error() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProfileInJson(dummyProfile.getAdditionalEmail(), dummyProfile.getMobile(), dummyProfile.getAddress()));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void createProfile_MissingAttribute_Error() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/")
                .header(HttpHeaders.AUTHORIZATION, dummyToken)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void createProfile_InvalidInput_Error() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/")
                .header(HttpHeaders.AUTHORIZATION, dummyToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProfileInJson("email", dummyProfile.getMobile(), dummyProfile.getAddress()));


        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void updateProfile_Profile_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/")
                .header(HttpHeaders.AUTHORIZATION, "xyz")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProfileInJson(dummyProfile.getAdditionalEmail(), dummyProfile.getMobile(), dummyProfile.getAddress()));
        System.out.println(createProfileInJson(dummyProfile.getAdditionalEmail(), dummyProfile.getMobile(), dummyProfile.getAddress()));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void updateProfile_Unauthorized_Error() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProfileInJson(dummyProfile.getAdditionalEmail(), dummyProfile.getMobile(), dummyProfile.getAddress()));


        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void updateProfile_MissingAttribute_Error() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/")
                .header(HttpHeaders.AUTHORIZATION, dummyToken)
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    public void updateProfile_InvalidInput_Error() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/")
                .header(HttpHeaders.AUTHORIZATION, dummyToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProfileInJson("email", dummyProfile.getMobile(), dummyProfile.getAddress()));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError())
                .andReturn();
    }


    private static String createProfileInJson(String additionalEmail, String phone, String address) {
        return "{ " +
                "\"additionalEmail\": \"" + additionalEmail + "\", " +
                " \"mobile\": \"" + phone + "\", " +
                "\"address\":\"" + address + "\"" +
                "}";
    }

}