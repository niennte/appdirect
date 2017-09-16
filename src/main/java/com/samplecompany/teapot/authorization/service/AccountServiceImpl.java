package com.samplecompany.teapot.authorization.service;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samplecompany.teapot.authorization.model.AccountDTO;
import com.samplecompany.teapot.authorization.model.UserDTO;
import com.samplecompany.teapot.authorization.entity.Account;
import com.samplecompany.teapot.authorization.entity.User;
import com.samplecompany.teapot.authorization.repository.AccountRepository;
import com.samplecompany.teapot.authorization.repository.UserRepository;


@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;


    // Accounts

    private Account readAccountById(Long accountId) {
        Account account = accountRepository.findOne(accountId);
        if (account == null) {
            throw new ObjectNotFoundException(accountId, Account.class.toString());
        }
        return account;
    }

    @Override
    @Transactional
    public void createAccount(AccountDTO accountDTO, UserDTO creatorUserDTO) {
        User creatorUser = creatorUserDTO.toUser();
        userRepository.save(creatorUser);
        creatorUserDTO.setId(creatorUser.getId());

        Account account = accountDTO.toAccount();
        account.addUser(creatorUser);
        accountRepository.save(account);
        accountDTO.setId(account.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDTO> readAccounts() {
        Pageable pageable = new PageRequest(0, 25, Direction.DESC, "id");
        Page<Account> page = accountRepository.findAll(pageable);
        List<AccountDTO> list = new ArrayList<>();
        for (Account account : page.getContent()) {
            AccountDTO accountDTO = new AccountDTO(account);
            list.add(accountDTO);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public AccountDTO readAccount(Long accountId) {
        Account account = readAccountById(accountId);
        return new AccountDTO(account);
    }

    @Override
    @Transactional
    public void updateAccount(AccountDTO accountDTO) {
        Account account = readAccountById(accountDTO.getId());
        account.setStatus(accountDTO.getStatus());
        account.setOwner(accountDTO.getOwner());
        account.setEditionCode(accountDTO.getEditionCode());
        account.setMaxUsers(accountDTO.getMaxUsers());
    }

    @Override
    @Transactional
    public void deleteAccount(Long accountId) {
        Account account = readAccountById(accountId);
        accountRepository.delete(account);
    }


	// Users

    private User readUserById(Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new ObjectNotFoundException(userId, Account.class.toString());
        }
        return user;
    }

    @Override
    @Transactional
    public void createUser(UserDTO userDTO, Long accountId) {
        Account account = readAccountById(accountId);
        User user = userDTO.toUser();
        account.addUser(user);
        userRepository.save(user);
        userDTO.setId(user.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> readUsers() {
        Pageable pageable = new PageRequest(0, 25, Direction.DESC, "id");
        Page<User> page = userRepository.findAll(pageable);
        List<UserDTO> list = new ArrayList<>();
        for (User user : page.getContent()) {
            UserDTO userDTO = new UserDTO(user);
            list.add(userDTO);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO readUser(Long userId) {
        User user = readUserById(userId);
        return new UserDTO(user);
    }

    @Override
    @Transactional
    public void removeUserFromAccount(String uuid, Long accountId) {
        userRepository.deleteUserByAppDirectUuidAndAccountId(uuid, accountId);
    }

}

