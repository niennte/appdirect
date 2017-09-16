package com.samplecompany.teapot.appdirect.integration.handlers;

import static com.appdirect.sdk.appmarket.events.APIResult.failure;
import static com.appdirect.sdk.appmarket.events.APIResult.success;

import com.appdirect.sdk.appmarket.AppmarketEventHandler;
import com.appdirect.sdk.appmarket.events.*;
import com.samplecompany.teapot.authorization.model.AccountDTO;
import com.samplecompany.teapot.authorization.service.AccountService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SubscriptionChangeHandler implements AppmarketEventHandler<SubscriptionChange> {

	@Autowired
	private AccountService accountService;

	@Override
	public APIResult handle(SubscriptionChange event) {

		APIResult result;
		Long accountId = Long.valueOf(event.getAccount().getAccountIdentifier());

		try {
			AccountDTO accountDTO = accountService.readAccount(accountId);

			// "Ownership change" use case
			accountDTO.setOwner(event.getOwner().getUuid());

			// Status change
			accountDTO.setStatus(event.getAccount().getStatus());

			// Edition
			accountDTO.setEditionCode(event.getOrder().getEditionCode());

			//	"Seat assign/unassign" use case
			// @TODO superclass this
			List<OrderItemInfo> items = event.getOrder().getItems();
			Integer maxUsers = null;
			for (OrderItemInfo item : items) {
				if (PricingUnit.USER.equals(item.getUnit())) {
					maxUsers = item.getQuantity();
				}
			}
			accountDTO.setMaxUsers(maxUsers);

			accountService.updateAccount(accountDTO);

			result = APIResult.success(String.format("Successfully updated account with identifier %s", accountId));

		} catch (ObjectNotFoundException | NumberFormatException e) {

			result = APIResult.failure(
					ErrorCode.ACCOUNT_NOT_FOUND,
					String.format(
							"Could not find account with identifier %s",
							accountId));
		}

		return result;
	}
}
