package com.app.ecom.store.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import com.app.ecom.store.constants.FieldNames;
import com.app.ecom.store.constants.RequestUrls;
import com.app.ecom.store.model.Role;
import com.app.ecom.store.service.RoleService;
import com.app.ecom.store.util.CommonUtil;
import com.app.ecom.store.validator.RoleValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RolesController {
	@Inject
	private RoleService roleService;
	
	@Inject
	private CommonUtil commonUtil;
	
	@Inject
	private RoleValidator roleValidator;
	
	@GetMapping(value = RequestUrls.ROLES)
    public String getRoles(Model model, @PageableDefault(page=1, size=10) Pageable pageable) {
		Page<Role> page = roleService.getRoles(pageable);
		model.addAttribute(FieldNames.PAGGING, commonUtil.getPagging(RequestUrls.ROLES, page.getNumber()+1, page.getTotalPages(), null));
		model.addAttribute(FieldNames.PAGE, page);
        return "roles";
    }
	
	@PostMapping(value = RequestUrls.ROLES)
	public String addCategory(Model model, @Valid Role role, BindingResult bindingResult) {
		roleValidator.validate(role, bindingResult);
		if (bindingResult.hasErrors()) {
			return "addRole";
		}
		
		roleService.addRole(role);
		return "redirect:"+RequestUrls.ROLES;
	}
	
	@GetMapping(value = RequestUrls.ADD_ROLE)
	public String addRole(Model model, @RequestParam(value = FieldNames.ID, required=false) Long id) {
		Role role;
		if(id != null){
			role = roleService.getRoleById(id);
		}else {
			role = new Role();
		}
		model.addAttribute(FieldNames.ROLE, role);
		return "addRole";
	}
}
