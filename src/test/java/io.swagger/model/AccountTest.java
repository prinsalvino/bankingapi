package io.swagger;

import io.swagger.model.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class AccountTest {
    @Test
    public void createAccountShouldNotBeNull() {
        Account account1 = new Account();
        assertNotNull(account1);
    }

}