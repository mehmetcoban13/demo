package com.example.demo.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.entity.Account;
import com.example.demo.dao.entity.Customer;
import com.example.demo.dao.repository.AccountRepository;
import com.example.demo.dao.repository.CustomerRepository;
import com.example.demo.domain.model.customer.RequestDeleteCustomer;
import com.example.demo.domain.model.customer.RequestSaveCustomer;
import com.example.demo.domain.model.customer.ResponseGenericForCustomer;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private AccountRepository accountRepository;
    
    public ResponseGenericForCustomer saveCustomer(RequestSaveCustomer request) {
        ResponseGenericForCustomer response = new ResponseGenericForCustomer();
        if(null == request){
            response.setSuccess(false);
            response.setMessage("The request must not be null!");
            return response;
        }
        if(null == request.getIdentityNo() || null == request.getName() || null == request.getSurname() || 
            null == request.getPhoneNumber() || null == request.getEmail()) {
                response.setMessage("Request must contain those: name, surname, identityNo, phoneNumber, and email, please make sure all is valid!");
                response.setSuccess(false);
                return response;
        }
        Customer customer = new Customer();
        customer.setIdentityNo(request.getIdentityNo());
        customer.setName(request.getName());
        customer.setSurname(request.getSurname());
        customer.setPhoneNumber(request.getPhoneNumber());
        customer.setEmail(request.getEmail());
        customer = repository.save(customer);
        response.setSuccess(true);
        response.setMessage("The customer has been created successfully with the customer id: " + customer.getId());
        return response;
    }
    
    public ResponseGenericForCustomer deleteCustomer(RequestDeleteCustomer request) {
        ResponseGenericForCustomer response = new ResponseGenericForCustomer();
        if(null == request){
            response.setSuccess(false);
            response.setMessage("The request must not be null!");
            return response;
        }
        if(null == request.getCustomerId() && null == request.getIdentityNo()){
            response.setMessage("Request must contain at least one of them: customerId or identityNo!");
            response.setSuccess(false);
            return response;
        }
        if(null != request.getCustomerId()){
            repository.deleteById(request.getCustomerId());
            List<Account> accountList = accountRepository.findAllByCustomerId(request.getCustomerId());
            accountRepository.deleteAll(accountList);
            response.setSuccess(true);
            response.setMessage("The customer and her/his accounts are successfully deleted!");
        }
        if(null != request.getIdentityNo()){
            Customer customer = repository.findByIdentityNo(request.getIdentityNo());
            if(null == customer){
                response.setMessage("No such customer found with identityNo: " + request.getIdentityNo());
                response.setSuccess(false);
                return response;
            }
            repository.delete(customer);
            List<Account> accountList = accountRepository.findAllByCustomerId(customer.getId());
            accountRepository.deleteAll(accountList);
            response.setSuccess(true);
            response.setMessage("The customer and her/his accounts are successfully deleted!");
        }
        return response;
    }
    
    public Customer findById(Long customerId) {
        return repository.findById(customerId).orElse(null);
    }
    
    public Customer findByIdentityNo(String identityNo) {
        return repository.findByIdentityNo(identityNo);
    }
}