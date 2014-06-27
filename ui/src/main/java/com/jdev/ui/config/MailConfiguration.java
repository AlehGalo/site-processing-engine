package com.jdev.ui.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@PropertySource("classpath:/config/application-dev.properties")
public class MailConfiguration implements EnvironmentAware {

    // spring.mail.host= localhost
    // spring.mail.port= 25
    // spring.mail.user=
    // spring.mail.password=
    // spring.mail.protocol= smtp
    // spring.mail.tls= false
    // spring.mail.auth= false

    private final String ENV_SPRING_MAIL = "spring.mail.";
    private final String DEFAULT_HOST = "127.0.0.1";
    private final String PROP_HOST = "host";
    private final String DEFAULT_PROP_HOST = "localhost";
    private final String PROP_PORT = "port";
    private final String PROP_USER = "user";
    private final String PROP_PASSWORD = "password";
    private final String PROP_PROTO = "protocol";
    private final String PROP_TLS = "tls";
    private final String PROP_AUTH = "auth";
    private final String PROP_SMTP_AUTH = "mail.smtp.auth";
    private final String PROP_STARTTLS = "mail.smtp.starttls.enable";
    private final String PROP_TRANSPORT_PROTO = "mail.transport.protocol";

    private final Logger log = LoggerFactory.getLogger(MailConfiguration.class);

    private RelaxedPropertyResolver propertyResolver;

    @Override
    public void setEnvironment(final Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, ENV_SPRING_MAIL);
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        log.debug("Configuring mail server");
        String host = propertyResolver.getProperty(PROP_HOST, DEFAULT_PROP_HOST);
        int port = propertyResolver.getProperty(PROP_PORT, Integer.class, 0);
        String user = propertyResolver.getProperty(PROP_USER);
        String password = propertyResolver.getProperty(PROP_PASSWORD);
        String protocol = propertyResolver.getProperty(PROP_PROTO);
        Boolean tls = propertyResolver.getProperty(PROP_TLS, Boolean.class, false);
        Boolean auth = propertyResolver.getProperty(PROP_AUTH, Boolean.class, false);

        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        if (host != null && !host.isEmpty()) {
            sender.setHost(host);
        } else {
            log.warn("Warning! Your SMTP server is not configured. We will try to use one on localhost.");
            log.debug("Did you configure your SMTP settings in your application.yml?");
            sender.setHost(DEFAULT_HOST);
        }
        sender.setPort(port);
        sender.setUsername(user);
        sender.setPassword(password);

        Properties sendProperties = new Properties();
        sendProperties.setProperty(PROP_SMTP_AUTH, auth.toString());
        sendProperties.setProperty(PROP_STARTTLS, tls.toString());
        sendProperties.setProperty(PROP_TRANSPORT_PROTO, protocol);
        sender.setJavaMailProperties(sendProperties);
        return sender;
    }

}
