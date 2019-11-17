package com.ga.usersapi.service;

import com.ga.usersapi.model.UserRole;

public interface UserRoleService {
    public UserRole createRole(UserRole newRole);

    public UserRole getRole(String roleName);
}
