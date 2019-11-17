package com.ga.usersapi.service;


import com.ga.usersapi.model.UserRole;
import com.ga.usersapi.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public UserRole createRole(UserRole newRole) {
        return userRoleRepository.save(newRole);
    }

    @Override
    public UserRole getRole(String roleName) {

        return userRoleRepository.getRoleByName(roleName);
    }
}
