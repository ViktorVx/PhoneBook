package org.pva.PhoneBook.controller;

import org.pva.PhoneBook.domain.Contact;
import org.pva.PhoneBook.domain.ContactRepo;
import org.pva.PhoneBook.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MainController {

    private final ContactRepo contactRepo;

    public MainController(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }


    @GetMapping("/main")
    public String index(Model model) {
        return "enter";
    }


    @GetMapping("/")
    public String index(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        List<Contact> contacts = (List<Contact>) contactRepo.findAll();

        model.addAttribute("contacts", contacts);
        model.addAttribute("name", name);
        return "main";
    }

    @PostMapping(value = "addContact")
    public String addContact(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName,
            Model model) {

        Contact contact = new Contact(firstName, lastName);
        contact.setOwner(user);
        contactRepo.save(contact);

        List<Contact> contacts = (List<Contact>) contactRepo.findAll();

        model.addAttribute("contacts", contacts);
        model.addAttribute("name", "user");
        return "main";
    }

    @RequestMapping(value = "filter", method = RequestMethod.POST)
    public String filterContacts(@RequestParam(name = "firstName") String firstName,
                                 @RequestParam(name = "lastName") String lastName,
                                 Model model) {

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
        model.addAttribute("name", "user");
        return "main";
    }

}