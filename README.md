# tea-pot

To run the application with Maven, execute the following command:

`mvn springboot:run`


To run the application in a Docker container pre-configured with JVM 1.8, 

1) execute the following command:

`mvn clean package docker:build`

2) run the container: 

`docker run -d --name=xxxx -p nnnn:8080 com.samplecompany.teapot/tea-pot
`
where *xxxx* is the desired name for the container, and *nnnn* is the desired port on the host machine.


# Description:

The “application” serves to subscribers symbolic "premium content" represented by a string “I’m a little teapot!”. That is a plain JSON object populated with minimal data (username, content, logout link)), but could be easily refactored to build a UI, and to pull in the module with the core application as a dependency:

`/member/content/teapot`

For convenience, I’ve also added two admin resources listing accounts and users:

`/admin/content/accounts`
`/admin/content/users`

 Logout is represented with /logout URL and removes the existing OpenID user session. 

`/logout`

The connector is using AppDirect SDK and implements 
- subscriptionOrderHandler
- subscriptionCancelHandler
- subscriptionChangeHandler
- subscriptionReactivatedHandler 
- userAssignmentHandler
- userUnassignmentHandler
- SSO (Open Id 2.0)
- (the rest of the events I haven’t been able to trigger on the test product and I’m clear enough as to what they might do). 


# Based on:
https://github.com/AppDirect/service-integration-sdk


# Smoke testing: 
Application is deployed to AWS at 

`http://ec2-34-226-122-141.compute-1.amazonaws.com`

and is plugged into Test product by Intrapolis (product not published, I'll need to invite tester as an admin user)
- subscription order - creator user able to login via SSO, view authorized content at member/content/teapot, admin/content/accounts, admin/content/users
- subscription cancel - not directly testable, if new account create, the old one doesn’t show up in admin/content/accounts
- subscription change - not directly testable, status in admin/content/accounts changes to ACTIVE
- user assign - user shows up in admin/content/accounts and admin/content/users, is able to login via SSO
- user unassign - remove user
- SSO (Open Id 2.0) - user is able to log in and view authorized resources (member/content/teapot, admin/content/accounts, admin/content/users), after running /logout, these are no longer served and the user is redirected to /


# To deploy with another AppDirect product:
1. In `[projectRoot]/src/main/resources/application.yml`
set connector.allowed.credentials property as follows:
`connector.allowed.credentials:  [developerKey]:[developerSecret]`
2. At the marketplace.appdirect.com developer account, point all integration URLS to 
`[siteUrl]/api/v1/integration/processEvent?eventUrl={eventUrl}`
3. At the marketplace.appdirect.com developer account, chose as authentication method "OpenId 2.0' and point login URL to 
`[siteUrl]/openid?openid_identifier={openid}` and set `[siteUrl]` as the Realm.
