package org.pva.PhoneBook.repository;

import org.pva.PhoneBook.domain.Contact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContactRepo extends CrudRepository<Contact, Long> {

    Iterable<Contact> findByFirstNameAndLastName(String firstName, String lastName);

    Iterable<Contact> findByFirstName(String firstName);

    Iterable<Contact> findByLastName(String lastName);

}
