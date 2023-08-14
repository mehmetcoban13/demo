package com.example.demo.domain.model.customer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDeleteCustomer {
    private Long customerId;
    private Long identityNo;
}
