package org.pva.PhoneBook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pva.PhoneBook.controller.MainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTest {

    @Autowired
    private MainController controller;

    @Test
    public void test() throws Exception {
        assertThat(controller).isNotNull();
    }

}
