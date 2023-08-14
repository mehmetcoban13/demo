package com.example.demo.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.dao.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByIdentityNo(Long identityNo);

    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    List<Customer> findByEmail(@Param("email") String email);
}