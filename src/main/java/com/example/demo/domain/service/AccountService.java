package com.example.demo.domain.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.enums.AccountTypeEnum;
import com.example.demo.common.enums.CurrencyTypeEnum;
import com.example.demo.dao.entity.Account;
import com.example.demo.dao.repository.AccountRepository;
import com.example.demo.dao.repository.CustomerRepository;
import com.example.demo.domain.model.account.RequestDeleteAccount;
import com.example.demo.domain.model.account.RequestDepositMoneyToAccount;
import com.example.demo.domain.model.account.RequestSaveAccount;
import com.example.demo.domain.model.account.RequestWithdrawMoneyFromAccount;
import com.example.demo.domain.model.account.ResponseGenericForAccount;

@Service
public class AccountService {
    @Autowired
    private AccountRepository repository;
    @Autowired
    private CustomerRepository customerRepository;
    
    private static final String IBAN_PREFIX = "TR66";
    private static final String BANK_CODE = "00062";
    private static final String BRANCH_CODE = "001201";
    private static final String IBAN_ZEROS = "000";
    private static final int accountNumberLength = 8;

    public ResponseGenericForAccount saveAccount(RequestSaveAccount request) {
        ResponseGenericForAccount response = new ResponseGenericForAccount();
        if(null == request){
            response.setMessage("Request must not be null!");
            response.setSuccess(false);
            return response;
        }

        if(null == request.getCustomerId()){
            response.setMessage("Customer ID must not be null!");
            response.setSuccess(false);
            return response;
        }

        if(!customerRepository.findById(request.getCustomerId()).isPresent()) {
            response.setMessage("No such customer found with the customer ID: " + request.getCustomerId());
            response.setSuccess(false);
            return response;
        }
        
        if(null == request.getCurrentBalance()){
            request.setCurrentBalance(BigDecimal.ZERO);
        }
        if(null == request.getAccountTypeEnum()){
            request.setAccountTypeEnum(AccountTypeEnum.DRAWING);
        }
        if(null == request.getCurrencyTypeEnum()){
            request.setCurrencyTypeEnum(CurrencyTypeEnum.TL);
        }
        Account account = new Account();
        account.setCustomerId(request.getCustomerId());
        account.setCurrentBalance(request.getCurrentBalance());
        account.setAccountType(request.getAccountTypeEnum());
        account.setCurrencyType(request.getCurrencyTypeEnum());
        account = repository.save(account);

        String accountNumber = "0".repeat(accountNumberLength) + String.valueOf(account.getId());
        accountNumber = accountNumber.substring(accountNumber.length() - accountNumberLength);
        String ibanNo = IBAN_PREFIX + BANK_CODE + BRANCH_CODE + IBAN_ZEROS + accountNumber;
        
        account.setIbanNo(ibanNo);
        repository.save(account);

        response.setSuccess(true);
        response.setMessage("The account is successfully created! Account ID: " + account.getId() + " || IBAN: " + account.getIbanNo());
        return response;
    }
    
    public ResponseGenericForAccount deleteAccount(RequestDeleteAccount request) {
        ResponseGenericForAccount response = new ResponseGenericForAccount();
        if(null != request){
            if(null != request.getAccountId()){
                repository.deleteById(request.getAccountId());
                response.setSuccess(true);
                response.setMessage("The account is successfully deleted!");
            }
            else if(null != request.getIbanNo() && !request.getIbanNo().isEmpty()){
                Account account = repository.findByIbanNo(request.getIbanNo());
                if(null != account){
                    repository.delete(account);
                    response.setSuccess(true);
                    response.setMessage("The account is successfully deleted!");
                } else {
                    response.setMessage("No such account found with IBAN: " + request.getIbanNo());
                    response.setSuccess(false);
                }
            } else {
                response.setMessage("Request must contain at least one of them: accountId or ibanNo!");
                response.setSuccess(false);
            }
        } else {
            response.setMessage("Request must not be null!");
            response.setSuccess(false);
        }
        return response;
    }
    
    public List<Account> findAllAccountsByCustomerId(Long customerId) {
        if(null != customerId){
            return repository.findAllByCustomerId(customerId);
        }
        return null;
    }
    
    public Account findByIbanNo(String ibanNo) {
        if(null != ibanNo && !ibanNo.isEmpty()){
            return repository.findByIbanNo(ibanNo);
        }
        return null;
    }

    public ResponseGenericForAccount withdrawMoney(RequestWithdrawMoneyFromAccount request){
        ResponseGenericForAccount response = new ResponseGenericForAccount();
        Account account = null;
        if(null == request){
            response.setMessage("Request must not be null!");
            response.setSuccess(false);
            return response;
        }
        if(null == request.getAccountId() && (null == request.getIbanNo() || request.getIbanNo().isEmpty())){
            response.setMessage("Request must contain at least one of them: accountId or ibanNo!");
            response.setSuccess(false);
            return response;
        }
        if(null == request.getWithdrawAmount() || BigDecimal.ZERO.compareTo(request.getWithdrawAmount()) != -1){
            response.setMessage("Withdraw amount must be greater than zero!");
            response.setSuccess(false);
            return response;
        }

        if(null != request.getAccountId()) {
            account = repository.findById(request.getAccountId()).orElse(null);
        } else if(null != request.getIbanNo()){
            account = repository.findByIbanNo(request.getIbanNo());
        }
        
        if(null == account){
            response.setMessage("No such account found, please check your request!");
            response.setSuccess(false);
            return response;
        }

        if(account.getCurrentBalance().compareTo(request.getWithdrawAmount()) == -1){
            response.setMessage("The current balance is " + account.getCurrentBalance() + " " + account.getCurrencyType().toString() + 
                                " || The withdraw amount must not be greater than the current balance!");
            response.setSuccess(false);
            return response;
        }
        
        account.setCurrentBalance(account.getCurrentBalance().subtract(request.getWithdrawAmount()));
        account = repository.save(account);
        response.setSuccess(true);
        response.setMessage("The amount of " + request.getWithdrawAmount() + " is substracted from the account. " + 
                            "|| Current Balance: " + account.getCurrentBalance() + " " + account.getCurrencyType().toString());
        return response;
    }
    
    public ResponseGenericForAccount depositMoney(RequestDepositMoneyToAccount request){
        ResponseGenericForAccount response = new ResponseGenericForAccount();
        Account account = null;
        if(null == request){
            response.setMessage("Request must not be null!");
            response.setSuccess(false);
            return response;
        }
        if(null == request.getAccountId() && (null == request.getIbanNo() || request.getIbanNo().isEmpty())){
            response.setMessage("Request must contain at least one of them: accountId or ibanNo!");
            response.setSuccess(false);
            return response;
        }
        if(null == request.getDepositAmount() || BigDecimal.ZERO.compareTo(request.getDepositAmount()) != -1){
            response.setMessage("Deposit amount must be greater than zero!");
            response.setSuccess(false);
            return response;
        }

        if(null != request.getAccountId()) {
            account = repository.findById(request.getAccountId()).orElse(null);
        } else if(null != request.getIbanNo()){
            account = repository.findByIbanNo(request.getIbanNo());
        }
        
        if(null == account){
            response.setMessage("No such account found, please check your request!");
            response.setSuccess(false);
            return response;
        }
        
        account.setCurrentBalance(account.getCurrentBalance().add(request.getDepositAmount()));
        account = repository.save(account);
        response.setSuccess(true);
        response.setMessage("The amount of " + request.getDepositAmount() + " is added to the account. " + 
                            "|| Current Balance: " + account.getCurrentBalance() + " " + account.getCurrencyType().toString());
        return response;
    }
}