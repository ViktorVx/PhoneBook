package org.pva.PhoneBook.domain;

import org.springframework.data.repository.CrudRepository;

public interface ContactRepo extends CrudRepository<Contact, Long> {
}
