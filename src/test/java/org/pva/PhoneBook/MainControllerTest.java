package org.pva.PhoneBook;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pva.PhoneBook.controller.MainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/contacts-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/contacts-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController controller;

    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='navbarSupportedContent']/form[2]/button").string("admin"));
    }

    @Test
    public void contactListTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='contacts-table']/tbody/tr").nodeCount(7));
    }

    @Test
    public void filterContactTest() throws Exception {
        this.mockMvc.perform(get("/").param( "first_name", "Ivan").param("last_name", "Ivanov"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='contacts-table']/tbody/tr[@data-id=1]").exists());
    }

    @Test
    public void filterContactFirstNameTest() throws Exception {
        this.mockMvc.perform(get("/").param( "first_name", "Ivan"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='contacts-table']/tbody/tr[@data-id=1]").exists())
                .andExpect(xpath("//*[@id='contacts-table']/tbody/tr[@data-id=7]").exists());
    }

    @Test
    public void addContactTest() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/addContact")
                .file("file", "123".getBytes())
                .param("firstName", "Willy")
                .param("lastName", "Wonka")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='contacts-table']/tbody/tr").nodeCount(8))
                .andExpect(xpath("//*[@id='contacts-table']/tbody/tr[@data-id=10]/td[@data-id='1']").string("Willy"))
                .andExpect(xpath("//*[@id='contacts-table']/tbody/tr[@data-id=10]/td[@data-id='2']").string("Wonka"));


    }

}
