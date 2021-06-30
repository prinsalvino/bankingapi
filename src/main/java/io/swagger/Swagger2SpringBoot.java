package io.swagger;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import io.swagger.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.threeten.bp.LocalDate;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableOpenApi
@ComponentScan(basePackages = {"io.swagger", "io.swagger.api", "io.swagger.repository", "io.swagger.configuration"})
public class Swagger2SpringBoot implements CommandLineRunner {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService; //for test


    public static void main(String[] args) throws Exception {
        new SpringApplication(Swagger2SpringBoot.class).run(args);
    }

    @Override
    public void run(String... arg0) throws Exception {
        List<Account> accounts = new ArrayList<>();

        List<User> users = new ArrayList<>();

        User soumia = new User(1L, "SB", passwordEncoder.encode("pass123"), "soumia", "bouhouri", "sou@gmx.com", LocalDate.of(1993, 8, 02), "Rijswijk", "2282JV", "Rijswijk", "062535199", User.TypeEnum.CUSTOMER, true);
        User user = new User(3L, "user", passwordEncoder.encode("user123"), "user", "bouhouri", "sou@gmx.com", LocalDate.of(1993, 8, 02), "Rijswijk", "2282JV", "Rijswijk", "062535199", User.TypeEnum.CUSTOMER, true);
        User prins = new User(2L, "prinsalvino", passwordEncoder.encode("test123"), "prins", "alvino", "prinsalvino@gmx.com", LocalDate.of(1993, 8, 02), "Rijswijk", "1156AX", "Amsterdam", "062535199", User.TypeEnum.EMPLOYEE, true);

        users.add(soumia);
        users.add(prins);
        users.add(user);


        Account account1 = new Account(1l, Account.TypeEnum.CURRENT, Account.CurrencyEnum.EUR, true, "NL01INHO00000000010", 9989);
        Account account2 = new Account(1L, Account.TypeEnum.SAVINGS, Account.CurrencyEnum.EUR, true, "NL01INHO00000000080", 500);
        Account account3 = new Account(2L, Account.TypeEnum.CURRENT, Account.CurrencyEnum.EUR, true, "NL01INHO00000000090", 8887);

        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);

        accounts.forEach(accountRepository::save);
        users.forEach(userRepository::save);


        List<Transaction> transactions = new ArrayList<>();
        Transaction transaction1 = new Transaction("NL01INHO00000000010", "NL01INHO00000000080", 252d, "tikke pay", 1L, Transaction.TransactionTypeEnum.DEPOSIT);
        Transaction transaction2 = new Transaction("NL01INHO00000000080", "NL01INHO00000000090", 500d, "family support", 2L, Transaction.TransactionTypeEnum.TRANSFER);

        transactions.add(transaction1);
        transactions.add(transaction2);

        transactions.forEach(transactionRepository::save);

        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }


    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
