package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Account;
import io.swagger.model.AccountDto;
import io.swagger.service.AccountService;
import io.swagger.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-02T11:15:57.209Z[GMT]")
@RestController
public class AccountApiController implements AccountApi {

    private static final Logger log = LoggerFactory.getLogger(AccountApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationService authenticationService;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<List<Account>> getAccounts(@Valid @RequestParam(value = "limit", required = false, defaultValue = "3") Integer limit) {

        List<Account> accountArrayList = new ArrayList<>();
        if (limit < 0 ) {
            return new ResponseEntity<List<Account>>(HttpStatus.BAD_REQUEST);
        } else {
            for (Account account : accountService.getAccounts()) {
                if (!authenticationService.isEmployee()) {
                    if (account.getUserId().equals(authenticationService.getCurrentUser().getId())) {
                        accountArrayList.add(account);
                    }
                } else {
                    accountArrayList.add(account);
                }
            }
        }
        return new ResponseEntity<List<Account>>(accountArrayList.subList(0, limit), HttpStatus.OK)
                .status(200)
                .body(accountArrayList.subList(0, limit));
    }


    @Override
    public ResponseEntity<Account> getAccountByIBAN(String iban) throws NotFoundException {
        Account account = accountService.getAccountByIban(iban);
        if (!authenticationService.isEmployee()) {
            if (!account.getUserId().equals(authenticationService.getCurrentUser().getId())) {
                return new ResponseEntity<Account>(HttpStatus.FORBIDDEN);
            }
        }
        return ResponseEntity.status(200).body(account);
    }

    @Override
    public ResponseEntity addAccount(@Valid AccountDto accountDto) throws IllegalAccessException {

        if (!authenticationService.isEmployee()) {
                return new ResponseEntity<Account>(HttpStatus.FORBIDDEN);
        }else
        {
            Account account = new Account();
            account.setBalance(accountDto.getBalance());
            account.setActive(accountDto.getActive());
            account.setCurrency(accountDto.getCurrency());
            account.setIban(accountService.GenerateRandomIban());
            account.setType(accountDto.getType());
            account.setUserId(accountDto.getUserId());
            Account NewAccount = accountService.addAccount(account);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(NewAccount);
        }

    }


    @Override
    public void deleteAccount(String iban) throws NotFoundException {
        if (authenticationService.isEmployee()) {
            accountService.deleteAccount(iban);
        }

    }

    @Override
    public ResponseEntity updateAccount(String IBAN, @Valid AccountDto body) {
        // update account information can be updated by the customer
        // TODO add permission  later
        Account account = accountService.updateAccount(body, IBAN);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(account);
    }

}
