package org.pva.PhoneBook.service;

import org.pva.PhoneBook.domain.Role;
import org.pva.PhoneBook.domain.User;
import org.pva.PhoneBook.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Value("${hostname}")
    private String hostname;

    @Autowired
    MailSender mailSender;

    @Autowired
    PasswordEncoder passwordEncoder;

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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        //***
        user.setActivationCode(UUID.randomUUID().toString());

        userRepo.save(user);
        //***
        sendMessage(user);
        return true;
    }

    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format("Hello, %s!\n" +
                    "Welcome to Phonebook!\n" +
                    "Please, visit this link for account activation: http://%s/activation/%s",
                    user.getUsername(), hostname, user.getActivationCode());
//            String message = "Это тестовая информация! Не нужно фильтровать ее как спам!!!";
            mailSender.send(user.getEmail(), "Activation code", message);
        }
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

    public void updateUser(User user, String password, String email) {
        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) || (userEmail != null && !userEmail.equals(email));
        if (isEmailChanged) {
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
            if (!StringUtils.isEmpty(password)) {
                user.setPassword(passwordEncoder.encode(password));
            }
        }
        userRepo.save(user);
        if (isEmailChanged) {
            sendMessage(user);
        }


    }
}
