package com.jdev.ngui.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jdev.domain.dao.repository.PersistentTokenRepository;
import com.jdev.domain.dao.repository.UserRepository;
import com.jdev.domain.entity.PersistentToken;
import com.jdev.domain.entity.User;
import com.jdev.ngui.Application;
import com.jdev.ui.service.UserService;

/**
 * Test class for the UserResource REST controller.
 * 
 * @see UserService
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ActiveProfiles("dev")
public class UserServiceTest {

   @Autowired
    private PersistentTokenRepository persistentTokenRepository;

   @Autowired
    private UserRepository userRepository;

   @Autowired
    private UserService userService;

    @Test
    public void testRemoveOldPersistentTokens() {
        assertThat(persistentTokenRepository.findAll()).isEmpty();
        User admin = userRepository.findOne("admin");
        generateUserToken(admin, "1111-1111", new LocalDate());
        LocalDate now = new LocalDate();
        generateUserToken(admin, "2222-2222", now.minusDays(32));
        assertThat(persistentTokenRepository.findAll()).hasSize(2);
        userService.removeOldPersistentTokens();
        assertThat(persistentTokenRepository.findAll()).hasSize(1);
    }

    private void generateUserToken(final User user, final String tokenSeries,
            final LocalDate localDate) {
        PersistentToken token = new PersistentToken();
        token.setSeries(tokenSeries);
        token.setUser(user);
        token.setTokenValue(tokenSeries + "-data");
        token.setTokenDate(localDate);
        token.setIpAddress("127.0.0.1");
        token.setUserAgent("Test agent");
        persistentTokenRepository.saveAndFlush(token);
    }
}
