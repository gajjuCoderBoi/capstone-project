package com.ga.profileapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="profile")
public class Profile {

    /*************************************************************************
     *
     *      Profile Entity which behave like a table in the database.
     *
     *      Profile Entity has five properties as follows:
     *
     *      profileId -> Store the id of Profile (Primary Key)
     *      additionalEmail -> String type
     *      phone -> String type
     *      address -> String type
     *      userId -> Long type
     *
     *************************************************************************/


    @JsonIgnore
    @Id
    @Column(name = "profile_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long profileId;


    @Column(name = "additional_email")
    @NotBlank(message = "Additional Email field can not be blank")
    @Email(message = "Email should be valid")
    private String additionalEmail;

    @Column(name = "mobile")
    @NotBlank(message = "mobile field can not be blank")
    private String mobile;

    @Column(name = "address")
    @NotBlank(message = "address field can not be blank")
    private String address;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Transient
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAdditionalEmail() {
        return additionalEmail;
    }

    public void setAdditionalEmail(String additionalEmail) {
        this.additionalEmail = additionalEmail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
