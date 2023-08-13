package com.example.demo.domain.model.account;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDepositMoneyToAccount {
    private Long accountId;
    private String ibanNo;
    private BigDecimal depositAmount;
}
