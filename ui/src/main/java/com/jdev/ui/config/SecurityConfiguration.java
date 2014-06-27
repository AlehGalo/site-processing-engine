package com.jdev.ui.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;

import com.jdev.ui.security.AjaxAuthenticationFailureHandler;
import com.jdev.ui.security.AjaxAuthenticationSuccessHandler;
import com.jdev.ui.security.AjaxLogoutSuccessHandler;
import com.jdev.ui.security.AuthoritiesConstants;
import com.jdev.ui.security.Http401UnauthorizedEntryPoint;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:/config/application-dev.properties")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

    @Autowired
    private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    @Autowired
    private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

    @Autowired
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RememberMeServices rememberMeServices;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(final WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/bower_components/**").antMatchers("/fonts/**")
                .antMatchers("/images/**").antMatchers("/scripts/**").antMatchers("/styles/**")
                .antMatchers("/views/**").antMatchers("/swagger-ui/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
                .rememberMe().rememberMeServices(rememberMeServices)
                .key(env.getProperty("jhipster.security.rememberme.key")).and().formLogin()
                .loginProcessingUrl("/app/authentication")
                .successHandler(ajaxAuthenticationSuccessHandler)
                .failureHandler(ajaxAuthenticationFailureHandler).usernameParameter("j_username")
                .passwordParameter("j_password").permitAll().and().logout()
                .logoutUrl("/app/logout").logoutSuccessHandler(ajaxLogoutSuccessHandler)
                .deleteCookies("JSESSIONID").permitAll().and().csrf().disable().headers()
                .frameOptions().disable().authorizeRequests().antMatchers("/app/rest/register")
                .permitAll().antMatchers("/app/rest/activate").permitAll()
                .antMatchers("/app/rest/authenticate").permitAll().antMatchers("/app/rest/logs/**")
                .hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/app/**").authenticated()
                .antMatchers("/websocket/tracker").hasAuthority(AuthoritiesConstants.ADMIN)
                .antMatchers("/websocket/**").permitAll().antMatchers("/metrics/**")
                .hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/health/**")
                .hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/trace/**")
                .hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/dump/**")
                .hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/shutdown/**")
                .hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/beans/**")
                .hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/info/**")
                .hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/autoconfig/**")
                .hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/env/**")
                .hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/trace/**")
                .hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/api-docs/**")
                .hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/protected/**")
                .authenticated();

    }

    @EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
    private static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {
    }
}
