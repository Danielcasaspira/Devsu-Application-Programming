package com.devsu.financial.services;

import com.devsu.financial.model.Account;
import com.devsu.financial.model.AccountRequest;
import com.devsu.financial.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    private final ClientConsumer clientConsumer;


    public AccountService(AccountRepository accountRepository, ClientConsumer clientConsumer) {
        this.accountRepository = accountRepository;
        this.clientConsumer = clientConsumer;
    }

    public Account createAccount(AccountRequest accountRequest){

        Long clientId = accountRequest.getClientId();
        if (clientConsumer.getClient(clientId) == null) {
            throw new NoSuchElementException("Client not found");
        }

        Account account = new Account();
        account.setNumberAccount(accountRequest.getNumberAccount());
        account.setTypeAccount(accountRequest.getTypeAccount());
        account.setInitialBalance(accountRequest.getInitialBalance());
        account.setStatus(accountRequest.isStatus());
        account.setClientId(clientId);

        return accountRepository.save(account);
    }

    public Account updateAccount(Long id, Account account){

        Account existingAccount = accountRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Account not found"));

        existingAccount.setNumberAccount(account.getNumberAccount());
        existingAccount.setTypeAccount(account.getTypeAccount());
        existingAccount.setInitialBalance(account.getInitialBalance());
        existingAccount.setStatus(account.isStatus());

        return accountRepository.save(existingAccount);
    }

    public void deleteAccount(Long id) {

        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Account not found"));
        accountRepository.delete(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

}
