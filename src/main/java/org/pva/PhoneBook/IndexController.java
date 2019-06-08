package org.pva.PhoneBook;

import org.pva.PhoneBook.domain.Contact;
import org.pva.PhoneBook.domain.ContactRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final ContactRepo contactRepo;

    public IndexController(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

//    public MainController(PersonRepository personService) {
//        this.persons = personService;
//    }

    @GetMapping("/")
    public String index(Model model) {
//        List<Person> personList = (List<Person>) persons.findAll();
//        model.addAttribute("persons", personList);
        Contact contact = new Contact("Иван", "Иванов");
        contactRepo.save(contact);

        return "index";
    }

}