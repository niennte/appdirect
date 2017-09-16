package com.samplecompany.teapot.authorization.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.appdirect.sdk.appmarket.events.AccountStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.samplecompany.teapot.authorization.entity.Account;
import com.samplecompany.teapot.authorization.entity.Addon;
import com.samplecompany.teapot.authorization.entity.User;

import com.google.common.base.Preconditions;

@Data
@NoArgsConstructor
public class AccountDTO implements Serializable {
    private static final long serialVersionUID = 7541900923418018250L;

    private Long id;
    private String appDirectUuid;
    private String appDirectBaseUrl;
    private String editionCode;
    private AccountStatus status;
    private String owner;
    private Integer maxUsers = null;
    private String samlIdpEntityId;
    private String samlIdpMetadataUrl;
    private List<UserDTO> users = new ArrayList<>();
    private List<AddonDTO> addons = new ArrayList<>();
    
    public AccountDTO(Account account) {
        super();
        this.id = account.getId();
        this.appDirectUuid = account.getAppDirectUuid();
        this.appDirectBaseUrl = account.getAppDirectBaseUrl();
        this.editionCode = account.getEditionCode();
        this.status = account.getStatus();
        this.owner = account.getOwner();
        this.maxUsers = account.getMaxUsers();
        this.samlIdpEntityId = account.getSamlIdpEntityId();
        this.samlIdpMetadataUrl = account.getSamlIdpMetadataUrl();
        for (User user : account.getUsers()) {
            this.users.add(new UserDTO(user));
        }
        for (Addon addon : account.getAddons()) {
            this.addons.add(new AddonDTO(addon));
        }
    }

    public Account toAccount() {
        Account account = new Account();
        account.setAppDirectUuid(getAppDirectUuid());
        account.setEditionCode(getEditionCode());
        account.setStatus(getStatus());
        account.setOwner(getOwner());
        account.setMaxUsers(getMaxUsers());
        account.setAppDirectBaseUrl(getAppDirectBaseUrl());
        account.setSamlIdpEntityId(getSamlIdpEntityId());
        account.setSamlIdpMetadataUrl(getSamlIdpMetadataUrl());
        return account;
    }
}

