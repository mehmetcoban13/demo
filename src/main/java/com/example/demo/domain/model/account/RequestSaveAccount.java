package com.example.demo.domain.model.account;

import java.math.BigDecimal;

import com.example.demo.common.enums.AccountTypeEnum;
import com.example.demo.common.enums.CurrencyTypeEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestSaveAccount {
    private Long customerId;
    private BigDecimal currentBalance;
    private CurrencyTypeEnum currencyTypeEnum;
    private AccountTypeEnum accountTypeEnum;
}
