package com.samplecompany.teapot.authorization.model;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.samplecompany.teapot.authorization.entity.User;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 267265287884714275L;

    private Long id;
    private String appDirectUuid;
    private String appDirectOpenId;
    private String email;
    private String firstName;
    private String lastName;
    private String language;
    private String locale;
    private String zipCode;
    private String department;
    private String timezone;
    private boolean admin = false;

    public UserDTO(User user) {
        this.id = user.getId();
        this.appDirectUuid = user.getAppDirectUuid();
        this.appDirectOpenId = user.getAppDirectOpenId();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.language = user.getLanguage();
        this.locale = user.getLocale();
        this.zipCode = user.getZipCode();
        this.department = user.getDepartment();
        this.timezone = user.getTimezone();
        this.admin = user.isAdmin();
    }

    public User toUser() {
        User user = new User();
        user.setAppDirectUuid(getAppDirectUuid());
        user.setAppDirectOpenId(getAppDirectOpenId());
        user.setEmail(getEmail());
        user.setFirstName(getFirstName());
        user.setLastName(getLastName());
        user.setLanguage(getLanguage());
        user.setLocale(getLocale());
        user.setZipCode(getZipCode());
        user.setDepartment(getDepartment());
        user.setTimezone(getTimezone());
        user.setAdmin(isAdmin());
        return user;
    }
}
