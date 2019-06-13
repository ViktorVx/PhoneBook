package org.pva.PhoneBook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class Main {

    //todo add thymeleaf templates

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
