package com.ga.usersapi.config;


import com.ga.usersapi.model.User;
import com.ga.usersapi.model.UserRole;
import com.ga.usersapi.repository.UserRepository;
import com.ga.usersapi.repository.UserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/*************************************************************************
 *  The DatabaseLoader class loads some records to the users database to
 *  help with testing and development
 *************************************************************************/
@Service
public class DatabaseLoader {

    private static final Logger log = LoggerFactory.getLogger(DatabaseLoader.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @PostConstruct
    public void init() {
        UserRole DBA = new UserRole("ROLE_DBA");
        UserRole ADMIN = new UserRole("ROLE_ADMIN");
        UserRole USER = new UserRole("ROLE_USER");
        log.info("Saved {}", userRoleRepository.save(DBA));
        log.info("Saved {}", userRoleRepository.save(ADMIN));
        log.info("Saved {}", userRoleRepository.save(USER));


        User batman = new User(1L, "batman@email.com","bat","batman");
        batman.getRoles().add(USER);

        User superman = new User(2L, "superman@email.com","super","superman");
        superman.getRoles().add(USER);
        log.info("Saved {}", userRepository.save(batman));
        log.info("Saved {}", userRepository.save(superman));
    }
}