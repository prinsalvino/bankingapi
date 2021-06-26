package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.AccountDto;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.Random;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationService authenticationService;

    

    public List<Account> getAccounts() {
        return (List<Account>) accountRepository.findAll();
    }

    public Account getAccountByIban(String iban) {
        Account account = accountRepository.getAccountByIban(iban);
        return account;
    }

    public Account addAccount(Account accountBody)  {
        if (authenticationService.isEmployee()) {
            if (getAccountByIban(accountBody.getIban()) == null) {
                accountRepository.save(accountBody);
            }
        }
        return accountBody;
    }

    @DeleteMapping
    public Void deleteAccount(String iban) {
        // TODO delete an account and its transactions

        if (authenticationService.isEmployee()) {
            if (accountRepository.getAccountByIban(iban) != null) {
                accountRepository.deleteById(iban);
            }
        }
        return null;
    }

    @PutMapping
    public Account updateAccount(AccountDto newUpdatedAccount, String iban) {
        Account accountToUpdate = accountRepository.getAccountByIban(iban);
        if (authenticationService.isEmployee()) {
            if (accountRepository.getAccountByIban(iban) != null) {
                accountToUpdate.setActive(newUpdatedAccount.getActive());
                accountToUpdate.setType(newUpdatedAccount.getType());
                accountToUpdate.setCurrency(newUpdatedAccount.getCurrency());
                accountRepository.save(accountToUpdate);
            }
        }
        return accountToUpdate;
    }

    public String GenerateRandomIban() {
        Random random = new Random();
        StringBuilder iban = new StringBuilder("NL");
        for (int i = 0; i < 2; i++) {
            int num = random.nextInt(10);
            iban.append(num);
        }
        iban.append("INHO0");
        for (int i = 0; i < 10; i++) {
            int num = random.nextInt(10);
            iban.append(num);
        }
        return iban.toString();
    }

}
