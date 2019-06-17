package org.pva.PhoneBook.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UploadsController {

    @GetMapping("/uploads")
    public String uploads() {
        return "uploads";
    }

}
