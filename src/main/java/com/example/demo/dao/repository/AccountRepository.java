package com.example.demo.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dao.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByCustomerId(Long customerId);

    @Query("SELECT a FROM Account a WHERE a.ibanNo = :ibanNo")
    Account findByIbanNo(@Param("ibanNo") String ibanNo);
}