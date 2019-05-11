package com.app.ecom.store.controller;

import java.util.Calendar;

import javax.inject.Inject;

import com.app.ecom.store.constants.RequestUrls;
import com.app.ecom.store.dto.UserDto;
import com.app.ecom.store.model.User;
import com.app.ecom.store.model.UserToken;
import com.app.ecom.store.service.UserService;
import com.app.ecom.store.service.UserTokenService;
import com.app.ecom.store.validator.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class SignupController {
    @Inject
    private UserService userService;

    @Inject
    private UserValidator userValidator;
    
    @Inject
    private UserTokenService userTokenService;
    
    @GetMapping(value = RequestUrls.REGISTRATION)
    public String registration(Model model) {
        model.addAttribute("userForm", new UserDto());
        return "registration";
    }

    @PostMapping(value = RequestUrls.REGISTRATION)
    public String registration(@ModelAttribute("userForm") UserDto userDto, BindingResult bindingResult, Model model, WebRequest request) {
        userValidator.validate(userDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        User user = userService.createUser(userDto);
        userService.sendVerificationLink(user, request);
        model.addAttribute("message", "Acount activation link has been sent to your email.");
        return "login";
    }
    
    @GetMapping(value = RequestUrls.REGISTRATION_CONFIRM)
    public String confirmRegistration(WebRequest request, Model model, @RequestParam String token) {
        UserToken userToken = userTokenService.getUserToken(token);
        
        if (userToken == null) {
            model.addAttribute("message", "Invalid token");
            return RequestUrls.LOGIN;
        }
         
        User user = userToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((userToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", "Acount activation link has been expired.");
            return RequestUrls.LOGIN;
        } 
        user.setIsEnabled(true);
        userService.update(user);
        model.addAttribute("message", "Your acount has been activated now.");
        return RequestUrls.LOGIN; 
    }
}
