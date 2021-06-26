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

    public List<Transaction> getTransactionByUserPerformingId(Long userPerformingId) //getting transactions from the user performing
    {
        //CONFIG USER ACCESS LATER
        return (List<Transaction>) transactionRepository.findTransactionByUserPerformingId(userPerformingId);
    }

    public boolean checkBalance(String iban, Double amount){
        Account account = accountService.getAccountByIban(iban);
        if(account.getIban() == iban){

            // convert double amount type into integer
            Integer amt = amount.intValue();

            if(account.getBalance() > amt){
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
        transactionRepository.save(transaction);
    }


}