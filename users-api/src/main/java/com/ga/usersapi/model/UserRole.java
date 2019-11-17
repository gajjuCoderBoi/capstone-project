package com.ga.usersapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column
    private String name;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY ,mappedBy = "roles", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private List<User> users;

    public UserRole() {
    }

    public UserRole(String name) {
        this.name = name;
    }

    public UserRole(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
