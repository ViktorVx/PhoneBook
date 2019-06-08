package org.pva.PhoneBook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
//    private final PersonRepository persons;

//    public MainController(PersonRepository personService) {
//        this.persons = personService;
//    }

    @GetMapping("/")
    public String index(Model model) {
//        List<Person> personList = (List<Person>) persons.findAll();
//        model.addAttribute("persons", personList);
        return "index";
    }

}