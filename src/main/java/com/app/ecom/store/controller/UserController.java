package com.app.ecom.store.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.app.ecom.store.constants.FieldNames;
import com.app.ecom.store.constants.RequestUrls;
import com.app.ecom.store.dto.UserDto;
import com.app.ecom.store.model.User;
import com.app.ecom.store.service.UserService;
import com.app.ecom.store.util.CommonUtil;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
	@Inject
	private UserService userService;

	@Inject
	private CommonUtil commonUtil;
	
	@Inject
	private Environment environment;

	@GetMapping(value = RequestUrls.USERS)
	public String getUsers(Model model, @PageableDefault(page=1, size=10) Pageable pageable) {
		Page<User> page = userService.getUsers(pageable);
		model.addAttribute(FieldNames.PAGGING, commonUtil.getPagging(RequestUrls.USERS, page.getNumber()+1, page.getTotalPages(), null));
		model.addAttribute(FieldNames.PAGE, page);
		return "users";
	}
	
	@Transactional
	@PostMapping(value = RequestUrls.EDIT_USERS)
	public String editUser(Model model, @ModelAttribute("user") User user, HttpServletRequest request, HttpServletResponse response) {
		userService.update(user);
		userService.updateLocale(request, response, user.getLanguage());
		return "myAccount";
	}
	
	@GetMapping(value = RequestUrls.EDIT_PROFILE)
	public String getUser(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username);
		java.util.Map<String, String> languages = new java.util.HashMap<>();
		languages.put("en", "English");
		languages.put("hi", "Hindi");
		languages.put("fr", "French");
		model.addAttribute("languages", languages);
		model.addAttribute("user", user);
		return "editProfile";
	}
	
	@GetMapping(value = RequestUrls.MY_ACCOUNT)
	public String myAccount(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findByUsername(username);
		model.addAttribute("user", user);
		return "myAccount";
	}
	
	@GetMapping(value = RequestUrls.FAILURE+"/{code}")
	public String showError(Model model, @PathVariable(FieldNames.CODE) Integer code) {
		model.addAttribute(FieldNames.CODE, code);
		model.addAttribute(FieldNames.MESSAGE, environment.getProperty(String.valueOf(code)));
		return "failure";
	}
	
	@GetMapping(value = "/users/search")
	@ResponseBody
	public List<UserDto> searchCustomer(@RequestParam(required = true) String mobileOrName) {
		return userService.getUserByMobileOrName(mobileOrName);
	}
}
