package com.samplecompany.teapot.content.controller;


import com.samplecompany.teapot.authorization.config.ServiceUri;
import com.samplecompany.teapot.content.response.ContentResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(value = ServiceUri.MEMBER_RESOURCES, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MemberController {

    /**
     * POC premium content, served to subscribers
     */
    @RequestMapping("/teapot")
    public ResponseEntity<ContentResponse> renderPremiumContent(Principal principal) {

        String name = null;
        if (principal != null) {
            name = principal.getName();
        }
        ContentResponse response = new ContentResponse(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
