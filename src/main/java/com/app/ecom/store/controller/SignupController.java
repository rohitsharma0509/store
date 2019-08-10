package com.app.ecom.store.controller;

import java.util.Calendar;

import com.app.ecom.store.constants.RequestUrls;
import com.app.ecom.store.dto.UserDto;
import com.app.ecom.store.dto.UserTokenDto;
import com.app.ecom.store.model.User;
import com.app.ecom.store.service.UserService;
import com.app.ecom.store.service.UserTokenService;
import com.app.ecom.store.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
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
	
    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;
    
    @Autowired
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
        UserTokenDto userTokenDto = userTokenService.getUserToken(token);
        
        if (userTokenDto == null) {
            model.addAttribute("message", "Invalid token");
            return RequestUrls.LOGIN;
        }
         
        UserDto userDto = userTokenDto.getUserDto();
        Calendar cal = Calendar.getInstance();
        if ((userTokenDto.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", "Acount activation link has been expired.");
            return RequestUrls.LOGIN;
        }
        userDto.setIsEnabled(true);
        userService.updateUser(userDto);
        model.addAttribute("message", "Your acount has been activated now.");
        return RequestUrls.LOGIN; 
    }
}
