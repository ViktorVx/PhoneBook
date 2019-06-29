package org.pva.PhoneBook.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    String host;

    private static final String username = System.getenv("EMAIL");

    private static final String password = System.getenv("EMAIL_PASSWORD");

    @Value("${spring.mail.port}")
    int port;

    @Value("${spring.mail.protocol}")
    String protocol;

    @Value("${mail.debug}")
    String debug;

    @Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.setPort(port);

        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", debug);

        return mailSender;
    }

}
