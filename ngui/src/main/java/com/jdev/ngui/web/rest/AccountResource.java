package com.jdev.ngui.web.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.context.SpringWebContext;

import com.codahale.metrics.annotation.Timed;
import com.jdev.domain.dao.repository.PersistentTokenRepository;
import com.jdev.domain.dao.repository.UserRepository;
import com.jdev.domain.domain.Authority;
import com.jdev.domain.domain.PersistentToken;
import com.jdev.domain.domain.User;
import com.jdev.ngui.security.SecurityUtils;
import com.jdev.ngui.service.MailService;
import com.jdev.ngui.service.UserService;
import com.jdev.ngui.web.rest.dto.UserDTO;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/app")
public class AccountResource {

    private final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ApplicationContext applicationContext;

    @Inject
    private SpringTemplateEngine templateEngine;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;

    @Inject
    private PersistentTokenRepository persistentTokenRepository;

    @Inject
    private MailService mailService;

    /**
     * POST /rest/register -> register the user.
     */
    @RequestMapping(value = "/rest/register", method = RequestMethod.POST, produces = "application/json")
    @Timed
    public ResponseEntity<?> registerAccount(@RequestBody final UserDTO userDTO,
            final HttpServletRequest request, final HttpServletResponse response) {
        User user = userRepository.findOne(userDTO.getLogin());
        if (user != null) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        } else {
            user = userService.createUserInformation(userDTO.getLogin(), userDTO.getPassword(),
                    userDTO.getFirstName(), userDTO.getLastName(),
                    userDTO.getEmail().toLowerCase(), userDTO.getLangKey());
            final Locale locale = Locale.forLanguageTag(user.getLangKey());
            String content = createHtmlContentFromTemplate(user, locale, request, response);
            mailService.sendActivationEmail(user.getEmail(), content, locale);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /**
     * GET /rest/activate -> activate the registered user.
     */
    @RequestMapping(value = "/rest/activate", method = RequestMethod.GET, produces = "application/json")
    @Timed
    public ResponseEntity<String> activateAccount(@RequestParam(value = "key") final String key) {
        User user = userService.activateRegistration(key);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<String>(user.getLogin(), HttpStatus.OK);
    }

    /**
     * GET /rest/authenticate -> check if the user is authenticated, and return
     * its login.
     */
    @RequestMapping(value = "/rest/authenticate", method = RequestMethod.GET, produces = "application/json")
    @Timed
    public String isAuthenticated(final HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    /**
     * GET /rest/account -> get the current user.
     */
    @RequestMapping(value = "/rest/account", method = RequestMethod.GET, produces = "application/json")
    @Timed
    public ResponseEntity<UserDTO> getAccount() {
        User user = userService.getUserWithAuthorities();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        List<String> roles = new ArrayList<>();
        for (Authority authority : user.getAuthorities()) {
            roles.add(authority.getName());
        }
        return new ResponseEntity<>(
                new UserDTO(user.getLogin(), user.getPassword(), user.getFirstName(),
                        user.getLastName(), user.getEmail(), user.getLangKey(), roles),
                HttpStatus.OK);
    }

    /**
     * POST /rest/account -> update the current user information.
     */
    @RequestMapping(value = "/rest/account", method = RequestMethod.POST, produces = "application/json")
    @Timed
    public void saveAccount(@RequestBody final UserDTO userDTO) {
        userService.updateUserInformation(userDTO.getFirstName(), userDTO.getLastName(),
                userDTO.getEmail());
    }

    /**
     * POST /rest/change_password -> changes the current user's password
     */
    @RequestMapping(value = "/rest/account/change_password", method = RequestMethod.POST, produces = "application/json")
    @Timed
    public ResponseEntity<?> changePassword(@RequestBody final String password) {
        if (StringUtils.isEmpty(password)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        userService.changePassword(password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET /rest/account/sessions -> get the current open sessions.
     */
    @RequestMapping(value = "/rest/account/sessions", method = RequestMethod.GET, produces = "application/json")
    @Timed
    public ResponseEntity<List<PersistentToken>> getCurrentSessions() {
        User user = userRepository.findOne(SecurityUtils.getCurrentLogin());
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(persistentTokenRepository.findByUser(user), HttpStatus.OK);
    }

    /**
     * DELETE /rest/account/sessions?series={series} -> invalidate an existing
     * session.
     * 
     * - You can only delete your own sessions, not any other user's session -
     * If you delete one of your existing sessions, and that you are currently
     * logged in on that session, you will still be able to use that session,
     * until you quit your browser: it does not work in real time (there is no
     * API for that), it only removes the "remember me" cookie - This is also
     * true if you invalidate your current session: you will still be able to
     * use it until you close your browser or that the session times out. But
     * automatic login (the "remember me" cookie) will not work anymore. There
     * is an API to invalidate the current session, but there is no API to check
     * which session uses which cookie.
     */
    @RequestMapping(value = "/rest/account/sessions/{series}", method = RequestMethod.DELETE)
    @Timed
    public void invalidateSession(@PathVariable final String series)
            throws UnsupportedEncodingException {
        String decodedSeries = URLDecoder.decode(series, "UTF-8");
        User user = userRepository.findOne(SecurityUtils.getCurrentLogin());
        List<PersistentToken> persistentTokens = persistentTokenRepository.findByUser(user);
        for (PersistentToken persistentToken : persistentTokens) {
            if (StringUtils.equals(persistentToken.getSeries(), decodedSeries)) {
                persistentTokenRepository.delete(decodedSeries);
            }
        }
    }

    private String createHtmlContentFromTemplate(final User user, final Locale locale,
            final HttpServletRequest request, final HttpServletResponse response) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("user", user);
        variables.put("baseUrl", request.getScheme() + "://" + // "http" + "://
                request.getServerName() + // "myhost"
                ":" + request.getServerPort());
        IWebContext context = new SpringWebContext(request, response, servletContext, locale,
                variables, applicationContext);
        return templateEngine.process(MailService.EMAIL_ACTIVATION_PREFIX
                + MailService.TEMPLATE_SUFFIX, context);
    }
}