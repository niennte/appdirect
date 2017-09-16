package com.samplecompany.teapot.handlers;

import static com.appdirect.sdk.appmarket.events.APIResult.success;

import com.appdirect.sdk.appmarket.AppmarketEventHandler;
import com.appdirect.sdk.appmarket.events.APIResult;
import com.appdirect.sdk.appmarket.events.SubscriptionChange;

public class SubscriptionChangeHandler implements AppmarketEventHandler<SubscriptionChange> {
	@Override
	public APIResult handle(SubscriptionChange event) {
		// TODO: to implement

		//	event.getOwner() returns the user that owns the subscription: if it differs from the previous value known
		// to the connector, then it is an update. This is the "ownership change" use case

		//	event.getAccount() returns an object that contains information about the account and its status on the
		// Marketplace; Status changes could be retrieved this way

		//	event.getOrder() contains information about the order, most notably the edition
		//	event.getOrder().getItems() would return the order items. This would give information about seat changes:
		//	For example, if the order contains an item with 'quantity' = 5 and pricing unit USER, that would indicate
		//	a change of seats in the application. This is the "seat assign/unassign" use case
		return success("fake success message");
	}
}
