package com.samplecompany.teapot;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.appdirect.sdk.ConnectorSdkConfiguration;
import com.appdirect.sdk.appmarket.DeveloperSpecificAppmarketCredentialsSupplier;
import com.appdirect.sdk.credentials.StringBackedCredentialsSupplier;

import com.samplecompany.teapot.appdirect.integration.handlers.EventHandlersConfiguration;
import com.samplecompany.teapot.authorization.config.WebSecurityConfiguration;

@Configuration
@Import({
		ConnectorSdkConfiguration.class,
		EventHandlersConfiguration.class,
		WebSecurityConfiguration.class
})
@ComponentScan(basePackages = {
		"com.samplecompany.teapot.authorization",
		"com.samplecompany.teapot.content",
})
@EnableAutoConfiguration
public class RootConfiguration {

	@Bean
	public DeveloperSpecificAppmarketCredentialsSupplier environmentCredentialsSupplier(@Value("${connector.allowed.credentials}") String allowedCredentials) {
		return new StringBackedCredentialsSupplier(allowedCredentials);
	}

}
