package test.java.io.swagger.controller;

import io.swagger.model.Account;
import io.swagger.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountApiControllerTest {

    @MockBean
    AccountService accountService;
    @Autowired
    private MockMvc mvc;
    private Account account;

    @BeforeEach
    public void setup() {
        account = new Account(500l, Account.TypeEnum.CURRENT, Account.CurrencyEnum.EUR, true, "NL01INHO00000000010", 9989);
    }

    @Test
    public void whenAddAccountShouldReturnCreated() throws Exception {
        mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void getAccountsShouldReturnJsonArray() throws Exception {
        given(accountService.getAccounts()).willReturn(Arrays.asList(account));
        this.mvc.perform(get("/accounts")).andExpect(
                status().isOk());
    }


}