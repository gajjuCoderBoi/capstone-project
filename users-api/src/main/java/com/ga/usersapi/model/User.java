package com.ga.usersapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/*************************************************************************
 *
 *      User Entity which behave like a table in the database.
 *
 *      User Entity has four properties as follows:
 *
 *      userId -> Store the id of Profile (Primary Key) Long type
 *      email -> String type
 *      password -> String type
 *      username -> Long type
 *
 *************************************************************************/


@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "Email field cannot be blanked.")
    @Email(message = "Invalid Email.")
    private String email;


    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password field cannot be blanked.")
    private String password;

    @Column(name = "username", nullable = false)
    @NotBlank(message = "Username field cannot be blanked")
    private String username;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<UserRole> roles;

    public List<UserRole> getRoles() {
        if (roles == null) roles = new ArrayList<>();
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(Long userId, String email, String password, String username) {
        setUserId(userId);
        setEmail(email);
        setUsername(username);
        setPassword(new BCryptPasswordEncoder().encode(password));
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
