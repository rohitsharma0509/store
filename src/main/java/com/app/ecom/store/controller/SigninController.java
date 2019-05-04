package com.app.ecom.store.controller;

import javax.inject.Inject;

import com.app.ecom.store.constants.RequestUrls;
import com.app.ecom.store.model.User;
import com.app.ecom.store.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SigninController {
    @Inject
    private UserService userService;
    
    @GetMapping(value = RequestUrls.LOGIN)
    public String login(Model model, String logout) {
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }
    
    @GetMapping(value = RequestUrls.FORGET_PASSWORD)
    public String forgetPassword(Model model, String username) {
        User user = userService.findByUsername(username);
        return "login";
    }
}
