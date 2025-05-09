package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.entity.Account;
import com.example.exception.AccountConflictException;
import com.example.exception.InvalidAccountException;
import com.example.exception.UnauthorizedAccountException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;        
    }
    
    /**
     * register new account checks if account is valid
     * @param Account to be register 
     * @return data of the registered accout 
     */
    public Account registerNewAccount(Account account){
        boolean isValid = !account.getUsername().isBlank() && 
        account.getPassword().length() > 4;

        if(!isValid){
            throw new InvalidAccountException("Invalid Account Params");
        }  
        
        boolean exist = accountRepository.findByUsername(account.getUsername()).isPresent();

        if(exist){
            throw new AccountConflictException("Username alredy exists");
        }
        
        return accountRepository.save(account);
    }

    /**
     * checks if login is valid 
     * @param Account to be check
     * @return Account valid account
     */
    public Account loginAccount(Account account){
        Optional<Account> target = accountRepository.findByUsername(account.getUsername());

        if(target.isEmpty()|| !target.get().getPassword().equals(account.getPassword())){
            throw new UnauthorizedAccountException("Unauthorized Account");
        }
        return target.get();
    }
}
