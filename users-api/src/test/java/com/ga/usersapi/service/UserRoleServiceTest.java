package com.ga.usersapi.service;

import com.ga.usersapi.model.UserRole;
import com.ga.usersapi.repository.UserRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/*******************************************************************************************************
 * The UserRoleServiceTest class tests the UserRoleService class and its methods
 *  using Mockito and JUnit
 **************************************************************************/


@RunWith(MockitoJUnitRunner.class)
public class UserRoleServiceTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    UserRole userRole;

    @InjectMocks
    UserRoleServiceImpl userRoleService;

    @Before
    public void init(){
        userRole.setRoleId(1L);
        userRole.setName("ADMIN");
    }
    @Test
    public void createRole_Role_Success() {
        when(userRoleRepository.save(any())).thenReturn(userRole);
        UserRole actual = userRoleService.createRole(userRole);
        assertEquals(userRole.getRoleId(),actual.getRoleId());
    }

    @Test
    public void getRole_Role_Success() {
        when(userRoleRepository.getRoleByName("ADMIN")).thenReturn(userRole);

        UserRole actual = userRoleService.getRole("ADMIN");

        assertEquals(userRole.getRoleId(), actual.getRoleId());
    }
}