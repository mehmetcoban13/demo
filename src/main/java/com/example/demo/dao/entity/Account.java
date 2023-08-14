package com.example.demo.dao.entity;

import java.math.BigDecimal;

import com.example.demo.common.enums.AccountTypeEnum;
import com.example.demo.common.enums.CurrencyTypeEnum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long customerId;
    private String ibanNo;
    private BigDecimal currentBalance;
    private CurrencyTypeEnum currencyTypeEnum;
    private AccountTypeEnum accountTypeEnum;
    
}
