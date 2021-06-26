package io.swagger.api;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.TransactionDto;
import io.swagger.service.AuthenticationService;
import io.swagger.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-06-02T11:15:57.209Z[GMT]")
@RestController
public class TransactionsApiController implements TransactionsApi {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AuthenticationService authenticationService;

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }


    @Override
    public ResponseEntity<List<Transaction>> getTransactions(Integer limit) {
        List<Transaction> transactions =  transactionService.getTransactions();
        return new ResponseEntity<List<Transaction>>(transactions.subList(0,limit),HttpStatus.OK)
                .status(200)
                .body(transactions.subList(0,limit));
        // solved this

        // wait 5min ok
    }

    @Override
    public ResponseEntity<List<Transaction>> getTransactionByIban(String iban) throws NotFoundException {
        List<Transaction> transactions = (List<Transaction>) transactionService.getTransactionByIban(iban) ;
        return ResponseEntity
                .status(200)
                .body(transactions);
    }

/*
    @Override
    public ResponseEntity<List<Transaction>> getTransactionByUserPerformingId(String userPerformingId) throws NotFoundException {
        List<Transaction> transactions =
                (List<Transaction>) transactionService.getTransactionByUserPerformingId(Long.valueOf(userPerformingId)) ;
        return ResponseEntity
                .status(200)
                .body(transactions);
    }
*/

    @Override
    public void createTransaction(TransactionDto transactionDto) {
        Transaction NewTransaction = new Transaction();
        NewTransaction.setAccountFrom(transactionDto.getAccountFrom());
        NewTransaction.setAccountTo(transactionDto.getAccountTo());
        NewTransaction.setAmount(transactionDto.getAmount());
        NewTransaction.setDescription(transactionDto.getDescription());

        //*** addd it to dto
        //NewTransaction.setUserPerformingId(5l);
        NewTransaction.setUserPerformingId(authenticationService.getCurrentUser().getId());
        NewTransaction.setTransactionType(Transaction.TransactionTypeEnum.TRANSFER);

        OffsetDateTime dtm = OffsetDateTime.now();
        NewTransaction.setTimestamp(dtm);
        transactionService.createTransaction(NewTransaction);

    }

}