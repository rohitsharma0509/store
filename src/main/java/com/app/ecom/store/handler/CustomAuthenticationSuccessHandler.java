package com.app.ecom.store.handler;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.ecom.store.constants.RequestUrls;
import com.app.ecom.store.model.User;
import com.app.ecom.store.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Inject
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        setLocale(authentication, request, response);
        response.sendRedirect(RequestUrls.HOME);
    }

    protected void setLocale(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        if (authentication != null &&authentication.getPrincipal() != null) {
        	String username = authentication.getName();
            User user = userService.findByUsername(username);
            String locale = null == user || StringUtils.isEmpty(user.getLanguage()) ? "en" : user.getLanguage();
            userService.updateLocale(request, response, locale);
        }
    }
}