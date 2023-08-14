package com.example.demo.dao.entity;

import java.math.BigDecimal;

import com.example.demo.common.enums.AccountTypeEnum;
import com.example.demo.common.enums.CurrencyTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="ACCOUNT")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence")
    @SequenceGenerator(name = "account_sequence", sequenceName = "account_seq", initialValue = 10000000, allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    @Column(name = "CUSTOMER_ID")
    private Long customerId;
    @Column(name = "IBAN_NO", unique = true)
    private String ibanNo;
    @Column(name = "CURRENCT_BALANCE")
    private BigDecimal currentBalance;
    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCT_TYPE")
    private CurrencyTypeEnum currencyType;
    @Enumerated(EnumType.STRING)
    @Column(name = "ACCOUNT_TYPE")
    private AccountTypeEnum accountType;
    
}