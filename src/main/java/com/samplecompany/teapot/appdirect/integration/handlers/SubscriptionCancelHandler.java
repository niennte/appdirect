package com.samplecompany.teapot.appdirect.integration.handlers;


import com.appdirect.sdk.appmarket.AppmarketEventHandler;
import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.ErrorCode;
import com.appdirect.sdk.appmarket.events.SubscriptionCancel;
import com.samplecompany.teapot.authorization.service.AccountService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;


public class SubscriptionCancelHandler implements AppmarketEventHandler<SubscriptionCancel> {

	@Autowired
	private AccountService accountService;

	@Override
	public APIResult handle(SubscriptionCancel event) {

		APIResult result;

		try {
			Long accountId = Long.valueOf(event.getAccountIdentifier());

			accountService.deleteAccount(accountId);

			result = new APIResult(
					true,
					String.format(
							"Successfully deleted account with identifier %s",
							event.getAccountIdentifier()
					)
			);
		} catch (ObjectNotFoundException | NumberFormatException e) {

			result = new APIResult(false, String.format("Could not find account with identifier %s", event.getAccountIdentifier()));
			result.setErrorCode(ErrorCode.ACCOUNT_NOT_FOUND);
		}

		return result;
	}
}
