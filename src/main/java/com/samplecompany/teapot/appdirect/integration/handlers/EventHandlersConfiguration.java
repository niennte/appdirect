package com.samplecompany.teapot.appdirect.integration.handlers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.appdirect.sdk.appmarket.AppmarketEventHandler;
import com.appdirect.sdk.appmarket.events.SubscriptionCancel;
import com.appdirect.sdk.appmarket.events.SubscriptionChange;
import com.appdirect.sdk.appmarket.events.SubscriptionOrder;
import com.appdirect.sdk.appmarket.events.SubscriptionReactivated;
import com.appdirect.sdk.appmarket.events.UserAssignment;
import com.appdirect.sdk.appmarket.events.UserUnassignment;

@Configuration
public class EventHandlersConfiguration {

	@Primary
	@Bean
	public AppmarketEventHandler<SubscriptionOrder> subscriptionOrderHandler() {
		return new SubscriptionOrderHandler();
	}

	@Primary
	@Bean
	public AppmarketEventHandler<SubscriptionCancel> subscriptionCancelHandler() {
		return new SubscriptionCancelHandler();
	}

	@Primary
	@Bean
	public AppmarketEventHandler<SubscriptionChange> subscriptionChangeHandler() {
		return new SubscriptionChangeHandler();
	}

	@Primary
	@Bean
	public AppmarketEventHandler<SubscriptionReactivated> subscriptionReactivatedAppmarketEventHandler() {
		return new SubscriptionReactivatedHandler();
	}

	@Primary
	@Bean
	public AppmarketEventHandler<UserAssignment> userAssignmentAppmarketEventHandler() {
		return new UserAssignmentHandler();
	}

	@Primary
	@Bean
	public AppmarketEventHandler<UserUnassignment> userUnassignmentAppmarketEventHandler() {
		return new UserUnassignmentHandler();
	}
}
