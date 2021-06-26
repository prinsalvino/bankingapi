package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    public List<Transaction> getTransactions()
    {
        return (List<Transaction>) transactionRepository.findAll();
    }


    public List<Transaction> getTransactionByIban(String IBAN) //getting transactions from a specific user iban
    {
        //CONFIG USER ACCESS LATER
        return (List<Transaction>) transactionRepository.findTransactionByAccountFrom(IBAN);
    }

    public boolean checkBalance(String iban, Double amount){
        Account account = accountService.getAccountByIban(iban);
        if(account.getIban().equals(iban)){
            // convert double amount type into integer
            Integer amt = amount.intValue();
            if(account.getBalance() >= amt){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public void createTransaction(Transaction transaction){
        Account accFrom = accountService.getAccountByIban(transaction.getAccountFrom());
        Account accTo = accountService.getAccountByIban(transaction.getAccountTo());
        accFrom.setBalance(accFrom.getBalance() - transaction.getAmount().intValue());
        accTo.setBalance(accTo.getBalance() + transaction.getAmount().intValue());
        accountService.updateBalance(accFrom);
        accountService.updateBalance(accTo);
        transactionRepository.save(transaction);
    }


}