package com.samplecompany.teapot.appdirect.integration.handlers;

import com.appdirect.sdk.appmarket.AppmarketEventHandler;
import com.appdirect.sdk.appmarket.events.*;

import com.samplecompany.teapot.authorization.model.AccountDTO;
import com.samplecompany.teapot.authorization.model.UserDTO;
import com.samplecompany.teapot.authorization.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class SubscriptionOrderHandler implements AppmarketEventHandler<SubscriptionOrder> {

	private static final String ZIP_CODE_KEY = "zipCode";
	private static final String DEPARTMENT_KEY = "department";
	private static final String TIMEZONE_KEY = "timezone";

	@Autowired
	private AccountService accountService;

	@Override
	public APIResult handle(SubscriptionOrder event) {

		// Prep creator user data
		UserDTO creatorUserDTO = new UserDTO();
		creatorUserDTO.setAppDirectUuid(event.getPurchaserInfo().getUuid());
		creatorUserDTO.setAppDirectOpenId(event.getPurchaserInfo().getOpenId());
		creatorUserDTO.setEmail(event.getPurchaserInfo().getEmail());
		creatorUserDTO.setFirstName(event.getPurchaserInfo().getFirstName());
		creatorUserDTO.setLastName(event.getPurchaserInfo().getLastName());
		creatorUserDTO.setAdmin(true);
		if (event.getPurchaserInfo().getAttributes() != null) {
			creatorUserDTO.setZipCode(event.getPurchaserInfo().getAttributes().get(ZIP_CODE_KEY));
			creatorUserDTO.setDepartment(event.getPurchaserInfo().getAttributes().get(DEPARTMENT_KEY));
			creatorUserDTO.setTimezone(event.getPurchaserInfo().getAttributes().get(TIMEZONE_KEY));
		}

		// Prep account data
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setAppDirectUuid(event.getCompanyInfo().getUuid());
		accountDTO.setEditionCode(event.getOrderInfo().getEditionCode());

		// @TODO find out how to obtain initial status
		// AccountStatus.FREE_TRIAL vs AccountStatus.ACTIVE
		accountDTO.setStatus(AccountStatus.INITIALIZED);
		accountDTO.setOwner(event.getPurchaserInfo().getUuid());


		List<OrderItemInfo> items = event.getOrderInfo().getItems();
		Integer maxUsers = null;
		for (OrderItemInfo item : items) {
			if (PricingUnit.USER.equals(item.getUnit())) {
				maxUsers = item.getQuantity();
			}
		}
		accountDTO.setMaxUsers(maxUsers);

		accountDTO.setAppDirectBaseUrl(event.getMarketplaceUrl());

		Optional<String> samlIdpUrl = event.getSamlIdpUrl();
		if (samlIdpUrl.isPresent()) {

			// @TODO implement SAML data fetch
			// or save the Url here
			accountDTO.setSamlIdpEntityId(null);
			accountDTO.setSamlIdpMetadataUrl(null);
		}

		// Persist account and creator
		accountService.createAccount(accountDTO, creatorUserDTO);

		APIResult result = APIResult.success("Created account " + accountDTO.getId().toString());
		result.setAccountIdentifier(accountDTO.getId().toString());
		result.setUserIdentifier(creatorUserDTO.getId().toString());
		return result;
	}
}
