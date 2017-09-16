package com.samplecompany.teapot.appdirect.integration.handlers;

import com.appdirect.sdk.appmarket.AppmarketEventHandler;
import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.ErrorCode;
import com.appdirect.sdk.appmarket.events.UserAssignment;
import com.appdirect.sdk.appmarket.events.UserInfo;
import com.samplecompany.teapot.authorization.model.UserDTO;
import com.samplecompany.teapot.authorization.service.AccountService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

public class UserAssignmentHandler implements AppmarketEventHandler<UserAssignment> {

	@Autowired
	private AccountService accountService;

	private static final String ZIP_CODE_KEY = "zipCode";
	private static final String DEPARTMENT_KEY = "department";
	private static final String TIMEZONE_KEY = "timezone";
	private static final String APP_ADMIN = "appAdmin";

	@Override
	public APIResult handle(UserAssignment event) {

		APIResult result;

		try {

			Long accountId = Long.valueOf(event.getAccountId());
			UserInfo userInfo = event.getAssignedUser();
			UserDTO userDTO = new UserDTO();
			userDTO.setAppDirectUuid(userInfo.getUuid());
			userDTO.setAppDirectOpenId(userInfo.getOpenId());
			userDTO.setEmail(userInfo.getEmail());
			userDTO.setFirstName(userInfo.getFirstName());
			userDTO.setLastName(userInfo.getLastName());
			Boolean admin = false;
			if (userInfo.getAttributes() != null) {
				userDTO.setZipCode(userInfo.getAttributes().get(ZIP_CODE_KEY));
				userDTO.setDepartment(userInfo.getAttributes().get(DEPARTMENT_KEY));
				userDTO.setTimezone(userInfo.getAttributes().get(TIMEZONE_KEY));
				admin = Boolean.parseBoolean(userInfo.getAttributes().get(APP_ADMIN));
			}
			userDTO.setAdmin(admin);

			accountService.createUser(userDTO, accountId);

			result = APIResult.success("Created user: " + userDTO.getAppDirectUuid());
			result.setUserIdentifier(userDTO.getId().toString());

		} catch (ObjectNotFoundException | NumberFormatException e) {

			result = APIResult.failure(
					ErrorCode.ACCOUNT_NOT_FOUND,
					String.format(
							"Could not find account with identifier %s",
							event.getAccountId()));
		}

		return result;
	}
}
