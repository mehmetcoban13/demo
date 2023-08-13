package com.example.demo.dao.entity;

import java.math.BigDecimal;

import com.example.demo.common.enums.AccountTypeEnum;
import com.example.demo.common.enums.CurrencyTypeEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private String ibanNo;
    private BigDecimal currentBalance;
    private CurrencyTypeEnum currencyTypeEnum;
    private AccountTypeEnum accountTypeEnum;
    
}
