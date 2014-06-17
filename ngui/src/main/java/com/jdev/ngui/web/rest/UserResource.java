package com.jdev.ngui.web.rest;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.jdev.domain.dao.repository.UserRepository;
import com.jdev.domain.domain.User;
import com.jdev.ngui.security.AuthoritiesConstants;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/app")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

   @Autowired
    private UserRepository userRepository;

    /**
     * GET /rest/users/:login -> get the "login" user.
     */
    @RequestMapping(value = "/rest/users/{login}", method = RequestMethod.GET, produces = "application/json")
    @Timed
    @RolesAllowed(AuthoritiesConstants.ADMIN)
    public User getUser(@PathVariable final String login, final HttpServletResponse response) {
        log.debug("REST request to get User : {}", login);
        User user = userRepository.findOne(login);
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        return user;
    }
}
