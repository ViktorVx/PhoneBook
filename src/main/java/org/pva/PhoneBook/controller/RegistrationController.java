package org.pva.PhoneBook.controller;

import org.pva.PhoneBook.domain.User;
import org.pva.PhoneBook.domain.dto.CaptchaResponseDto;
import org.pva.PhoneBook.repository.UserRepo;
import org.pva.PhoneBook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Controller
public class RegistrationController {

//    @Value("${recaptcha.secret}")
    private String secret = System.getenv("RECAPTCHA_KEY");

    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user,
                          @RequestParam("g-recaptcha-response") String captchaResponse,
                          Model model) {
        //******
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);
        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha!");
        }
        //******

        if (response.isSuccess() && !userService.saveNewUser(user)) {
            model.addAttribute("message", "User exists");
            return "registration";
        }
        if (response.isSuccess()) {
            return "redirect:/login";
        } else {
            model.addAttribute("user", user);
            return "registration";
        }
    }

    @GetMapping("/activation/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("message", "Activation code is not found");
        }
        return "login";
    }
}
