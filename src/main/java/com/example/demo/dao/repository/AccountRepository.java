package com.example.demo.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.dao.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByCustomerId(Long customerId);

    @Query("SELECT * FROM Account WHERE ibanNo = :ibanNo")
    Account findByIbanNo(String ibanNo);
}
