package com.samplecompany.teapot.appdirect.integration.handlers;

import static com.appdirect.sdk.appmarket.events.APIResult.failure;

import com.appdirect.sdk.appmarket.AppmarketEventHandler;
import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.ErrorCode;
import com.appdirect.sdk.appmarket.events.UserUnassignment;
import com.samplecompany.teapot.authorization.model.AccountDTO;
import com.samplecompany.teapot.authorization.model.UserDTO;
import com.samplecompany.teapot.authorization.service.AccountService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

public class UserUnassignmentHandler implements AppmarketEventHandler<UserUnassignment> {

	@Autowired
	private AccountService accountService;

	@Override
	public APIResult handle(UserUnassignment event) {
		
		APIResult result;
		try {

			Long accountId = Long.valueOf(event.getAccountId());
			String appDirectUuid = event.getUnassignedUser().getUuid();

			accountService.removeUserFromAccount(appDirectUuid, accountId);

			result = APIResult.success(String.format(
					"Removed user with uuid %s from account %s",
					appDirectUuid,
					accountId.toString()));

		} catch (ObjectNotFoundException | NumberFormatException e) {

			result = APIResult.failure(
					ErrorCode.USER_NOT_FOUND,
					e.getMessage());
		}
		return result;

	}
}
