package org.pva.PhoneBook.service;

import org.pva.PhoneBook.domain.Role;
import org.pva.PhoneBook.domain.User;
import org.pva.PhoneBook.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    MailSender mailSender;

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean saveNewUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return false;
        }
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        //***
        user.setActivationCode(UUID.randomUUID().toString());

        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format("Hello, %s!\n" +
                    "Welcome to Phonebook!\n" +
                    "Please, visit this link for account activation: http://localhost:8080/activation/%s",
                    user.getUsername(), user.getActivationCode());
//            String message = "Это тестовая информация! Не нужно фильтровать ее как спам!!!";
            mailSender.send(user.getEmail(), "Activation code", message);
        }
        //***
        userRepo.save(user);
        return true;
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        userRepo.save(user);

        return true;
    }
}
