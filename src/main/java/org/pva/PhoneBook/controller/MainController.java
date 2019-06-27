package org.pva.PhoneBook.controller;

import org.pva.PhoneBook.domain.Contact;
import org.pva.PhoneBook.repository.ContactRepo;
import org.pva.PhoneBook.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class MainController {

    @Value("${upload.path}")
    private String uploadPath;

    private final ContactRepo contactRepo;

    public MainController(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }


    @RequestMapping("/main")
    public String main() {
        return "enter";
    }


    @RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
    public String index(@RequestParam(name = "firstName", defaultValue = "", required = false) String firstName,
                        @RequestParam(name = "lastName", defaultValue = "", required = false) String lastName,
                        Model model) {
        //***
        Iterable<Contact> contacts = contactRepo.findAll();
        if (!((firstName == null || firstName.equals("")) && (lastName == null || lastName.equals("")))) {
            if (firstName == null || firstName.equals("")) {
                contacts = contactRepo.findByLastName(lastName);
            } else if (lastName == null || lastName.equals("")) {
                contacts = contactRepo.findByFirstName(firstName);
            } else {
                contacts = contactRepo.findByFirstNameAndLastName(firstName, lastName);
            }
        }
        model.addAttribute("contacts", contacts);
        return "main";
    }

    @RequestMapping(value = "addContact", method = RequestMethod.POST)
    public String addContact(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "firstName", defaultValue = "", required = false) String firstName,
            @RequestParam(name = "lastName", defaultValue = "", required = false) String lastName,
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {

        Contact contact = new Contact(firstName, lastName);
        //***
        if (file != null) {
            File uploadDir = new File(uploadPath);
            System.out.println(uploadPath);
            if (!uploadDir.exists()) {
                System.out.println("No folder");
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName= uuidFile + "." + file.getOriginalFilename();
            System.out.println(uploadDir + "/" + resultFileName);

//            file.transferTo(new File(resultFileName));
            file.transferTo(new File(uploadDir + "/" + resultFileName));

            contact.setPhotoPath(resultFileName);
        }
        //***
        contact.setOwner(user);
        contactRepo.save(contact);

        List<Contact> contacts = (List<Contact>) contactRepo.findAll();

        model.addAttribute("contacts", contacts);
        model.addAttribute("name", "user");
        return "main";
    }

}