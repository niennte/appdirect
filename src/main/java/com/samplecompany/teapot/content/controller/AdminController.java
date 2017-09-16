package com.samplecompany.teapot.content.controller;


import com.samplecompany.teapot.authorization.config.ServiceUri;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samplecompany.teapot.authorization.model.AccountDTO;
import com.samplecompany.teapot.authorization.model.UserDTO;
import com.samplecompany.teapot.authorization.service.AccountService;


@RestController
@RequestMapping(value = ServiceUri.ADMIN_RESOURCES, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {
    @Autowired
    private AccountService accountService;

    @RequestMapping("users")
    public Resources<UserDTO> readUsers() {
        return new Resources<>(accountService.readUsers());
    }

    @RequestMapping("users/{userId}")
    public UserDTO readUser(@PathVariable("userId") Long userId) {
        return accountService.readUser(userId);
    }

    @RequestMapping("accounts")
    public Resources<AccountDTO> readAccounts() {
        return new Resources<>(accountService.readAccounts());
    }

    @RequestMapping("accounts/{accountId}")
    public AccountDTO readAccount(@PathVariable("accountId") Long accountId) {
        return accountService.readAccount(accountId);
    }
}

