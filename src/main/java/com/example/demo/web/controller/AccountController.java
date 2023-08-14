package com.example.demo.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.demo.domain.model.account.*;
import com.example.demo.domain.service.AccountService;
import com.example.demo.dao.entity.Account;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/save")
    public ResponseGenericForAccount saveAccount(@RequestBody RequestSaveAccount request) {
        return accountService.saveAccount(request);
    }

    @PostMapping("/delete")
    public ResponseGenericForAccount deleteAccount(@RequestBody RequestDeleteAccount request) {
        return accountService.deleteAccount(request);
    }

    @GetMapping("/customer/{customerId}")
    public List<Account> findAllAccountsByCustomerId(@PathVariable Long customerId) {
        return accountService.findAllAccountsByCustomerId(customerId);
    }

    @GetMapping("/iban/{ibanNo}")
    public Account findByIbanNo(@PathVariable String ibanNo) {
        return accountService.findByIbanNo(ibanNo);
    }

    @PostMapping("/withdraw")
    public ResponseGenericForAccount withdrawMoney(@RequestBody RequestWithdrawMoneyFromAccount request) {
        return accountService.withdrawMoney(request);
    }

    @PostMapping("/deposit")
    public ResponseGenericForAccount depositMoney(@RequestBody RequestDepositMoneyToAccount request) {
        return accountService.depositMoney(request);
    }
}