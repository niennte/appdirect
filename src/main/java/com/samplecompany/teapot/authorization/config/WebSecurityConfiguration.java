package com.samplecompany.teapot.authorization.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import com.samplecompany.teapot.authorization.service.OpenIdUserDetailsServiceImpl;
import static com.samplecompany.teapot.authorization.config.ServiceUri.*;


@Configuration
@Order(1000)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationUserDetailsService<OpenIDAuthenticationToken> openIdUserDetailsService() {
        return new OpenIdUserDetailsServiceImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(MEMBER_RESOURCES +"/*").authenticated()
                .antMatchers(ADMIN_RESOURCES +"/*").authenticated()
                .and()
                .csrf().disable()
                .logout().logoutSuccessUrl(LOGOUT_SUCCESS)
                .and()
                .openidLogin()
                .defaultSuccessUrl(LOGIN_SUCCESS, true)
                .loginPage(LOGIN_PAGE).permitAll().loginProcessingUrl(LOGIN_PROCESSING)
                .authenticationUserDetailsService(openIdUserDetailsService())
                .attributeExchange("https://marketplace.appdirect.com/openid/id/.*")
                .attribute("userUuid")
                .type("https://www.appdirect.com/schema/user/uuid")
                .required(true);

    }


}
