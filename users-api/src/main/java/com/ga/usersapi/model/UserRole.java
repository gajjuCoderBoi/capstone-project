package com.ga.usersapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;


/*************************************************************************
 *
 *      User Role which behave like a table in the database.
 *
 *      User Role has two properties as follows:
 *
 *      roleId -> Store the id of Profile (Primary Key) Long type
 *      name -> String type
 *
 *************************************************************************/

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

    @Override
    public String toString() {
        return "UserRole{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                '}';
    }
}
