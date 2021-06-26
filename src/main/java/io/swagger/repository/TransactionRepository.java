package io.swagger.repository;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.threeten.bp.OffsetDateTime;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {


    @Query("SELECT t from Transaction t where t.accountFrom =:accountFrom")
    List<Transaction> findTransactionByAccountFrom(@Param("accountFrom") String accountFrom);

    @Query("SELECT t from Transaction t where t.userPerformingId =:userPerformingId")
    List<Transaction> findTransactionByUserPerformingId(@Param("userPerformingId") Long userPerformingId);
}

