package com.samplecompany.teapot.appdirect.integration.handlers;

import static com.appdirect.sdk.appmarket.events.APIResult.success;

import com.appdirect.sdk.appmarket.AppmarketEventHandler;
import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.ErrorCode;
import com.appdirect.sdk.appmarket.events.SubscriptionReactivated;
import com.samplecompany.teapot.authorization.model.AccountDTO;
import com.samplecompany.teapot.authorization.service.AccountService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

public class SubscriptionReactivatedHandler implements AppmarketEventHandler<SubscriptionReactivated> {

	@Autowired
	private AccountService accountService;

	@Override
	public APIResult handle(SubscriptionReactivated event) {

		APIResult result;

		try {

			Long accountId = Long.valueOf(event.getAccountInfo().getAccountIdentifier());
			AccountDTO accountDTO = accountService.readAccount(accountId);
			accountDTO.setStatus(event.getAccountInfo().getStatus());
			accountService.updateAccount(accountDTO);

			result = APIResult.success(
					String.format(
							"Updated status in account with identifier %s",
							accountId));

		} catch (ObjectNotFoundException | NumberFormatException e) {

			result = APIResult.failure(
					ErrorCode.ACCOUNT_NOT_FOUND,
					String.format(
							"Could not find account with identifier %s",
							event.getAccountInfo().getAccountIdentifier()));

		}
		return result;
	}
}
