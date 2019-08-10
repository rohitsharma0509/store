package com.app.ecom.store.events;

import com.app.ecom.store.constants.RequestUrls;
import com.app.ecom.store.service.UserTokenService;
import com.app.ecom.store.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationCompleteListener implements ApplicationListener<RegistrationCompleteEvent> {
    
    @Autowired
    private EmailUtil emailUtil;
    
    @Autowired
    private UserTokenService verificationTokenService;

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        System.out.println("Listening event......");
        String token = verificationTokenService.createUserToken(event.getUser());
        String confirmationUrl = "http://localhost:8080"+event.getUrl() + RequestUrls.REGISTRATION_CONFIRM+"?token=" + token;
        emailUtil.sendEmail(new String[] {event.getUser().getEmail()}, null, null, "Registration Confirmation", confirmationUrl);
    }
}
