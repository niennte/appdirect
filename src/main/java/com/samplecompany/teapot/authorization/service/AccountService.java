package com.samplecompany.teapot.authorization.service;


import java.util.List;


import com.samplecompany.teapot.authorization.model.AccountDTO;
import com.samplecompany.teapot.authorization.model.UserDTO;


public interface AccountService {
	/*
	 * Account
	 */

    void createAccount(AccountDTO accountDTO, UserDTO adminDTO);

    List<AccountDTO> readAccounts();

    AccountDTO readAccount(Long accountId);

    void updateAccount(AccountDTO accountDTO);

    void deleteAccount(Long accountId);

	/*
	 * User
	 */

    void createUser(UserDTO userDTO, Long accountId);

    List<UserDTO> readUsers();

    UserDTO readUser(Long userId);

    void removeUserFromAccount(String uuid, Long accountId);


}
