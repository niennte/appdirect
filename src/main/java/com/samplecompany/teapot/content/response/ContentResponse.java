package com.samplecompany.teapot.content.response;


import lombok.Getter;
import org.springframework.hateoas.Link;


@Getter
public class ContentResponse {

    private String loggedInAs;
    private String premiumContent;
    private Link logout;

    public ContentResponse(String username) {
        this.loggedInAs = username;
        this.premiumContent = "I am a little Teapot!";
        this.logout = new Link("/logout", "logoutLink");
    }

}
