package org.pva.PhoneBook;

import org.pva.PhoneBook.domain.Contact;
import org.pva.PhoneBook.domain.ContactRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class IndexController {

    private final ContactRepo contactRepo;

    public IndexController(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    @GetMapping("/")
    public String index(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        List<Contact> contacts = (List<Contact>) contactRepo.findAll();

        model.addAttribute("contacts", contacts);
        model.addAttribute("name", name);
        return "main";
    }

    @PostMapping("addContact")
    public String addContact(@RequestParam(name = "firstName", required = true) String firstName,
                             @RequestParam(name = "lastName", required = true) String lastName,
                             Model model) {
        Contact contact = new Contact(firstName, lastName);
        contactRepo.save(contact);

        List<Contact> contacts = (List<Contact>) contactRepo.findAll();

        model.addAttribute("contacts", contacts);
        model.addAttribute("name", "user");
        return "main";
    }

    @PostMapping("filter")
    public String filterContacts(@RequestParam(name = "firstName", required = true) String firstName,
                                 @RequestParam(name = "lastName", required = true) String lastName,
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