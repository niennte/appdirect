package com.samplecompany.teapot.authorization.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;

import com.samplecompany.teapot.authorization.model.UserDTO;
import com.samplecompany.teapot.authorization.entity.User;
import com.samplecompany.teapot.authorization.repository.UserRepository;
import com.samplecompany.teapot.authorization.UserDetailsImpl;


public class OpenIdUserDetailsServiceImpl implements AuthenticationUserDetailsService<OpenIDAuthenticationToken> {
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
        String openId = token.getIdentityUrl();
        User user = userRepository.findOneByAppDirectOpenId(openId);
        if (user == null) {
            throw new UsernameNotFoundException(openId);
        }
        UserDTO userDTO = new UserDTO(user);
        return new UserDetailsImpl(userDTO);
    }
}