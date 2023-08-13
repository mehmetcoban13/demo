package com.example.demo.domain.model.account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDeleteAccount {
    private Long accountId;
    private String ibanNo;
}
