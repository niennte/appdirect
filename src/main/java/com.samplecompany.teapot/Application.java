package com.samplecompany.teapot;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

import com.appdirect.sdk.ConnectorSdkConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {
	public static void main(String... args) {
		SpringApplication.run(RootConfiguration.class, args);
	}

	/**
	 * Hack to make Spring Boot @Controller annotated classed to recognize the 'x-www-form-urlencoded' media type
	 *
	 * @param converters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		FormHttpMessageConverter converter = new FormHttpMessageConverter();
		MediaType mediaType = new MediaType("application", "x-www-form-urlencoded", Charset.forName("UTF-8"));
		converter.setSupportedMediaTypes(Collections.singletonList(mediaType));
		converters.add(converter);
		super.configureMessageConverters(converters);
	}
}
