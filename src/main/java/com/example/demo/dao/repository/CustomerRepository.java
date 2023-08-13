package com.example.demo.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.dao.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByIdentityNo(String identityNo);

    @Query("SELECT * FROM Customer WHERE email = :email")
    List<Customer> findByEmail(String email);
}