package com.ga.usersapi.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/***********************************************************************************
 * Spring will detect the instance of this class during application startup,
 * and register the DelegatingFilterProxy to use the springSecurityFilterChain
 * before any other registered Filter. It also register a ContextLoaderListener.
 */

public class SecurityConfigInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityConfigInitializer() {
        super(SecurityConfig.class);
    }
}