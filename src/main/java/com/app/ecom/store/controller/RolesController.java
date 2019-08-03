package com.app.ecom.store.controller;

import javax.validation.Valid;

import com.app.ecom.store.constants.FieldNames;
import com.app.ecom.store.constants.RequestUrls;
import com.app.ecom.store.dto.IdsDto;
import com.app.ecom.store.dto.RoleDto;
import com.app.ecom.store.model.Role;
import com.app.ecom.store.service.RoleService;
import com.app.ecom.store.util.CommonUtil;
import com.app.ecom.store.validator.RoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RolesController {
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private RoleValidator roleValidator;
	
	@GetMapping(value = RequestUrls.ROLES)
    public String getRoles(Model model, @PageableDefault(page=1, size=10) Pageable pageable) {
		Page<Role> page = roleService.getRoles(pageable);
		model.addAttribute(FieldNames.PAGGING, commonUtil.getPagging(RequestUrls.ROLES, page.getNumber()+1, page.getTotalPages(), null));
		model.addAttribute(FieldNames.PAGE, page);
        return "roles";
    }
	
	@PostMapping(value = RequestUrls.ROLES)
	public String addRole(Model model, @Valid RoleDto roleDto, BindingResult bindingResult) {
		roleValidator.validate(roleDto, bindingResult);
		if (bindingResult.hasErrors()) {
			return "addRole";
		}
		
		roleService.addRole(roleDto);
		return "redirect:"+RequestUrls.ROLES;
	}
	
	@GetMapping(value = RequestUrls.ADD_ROLE)
	public String addRole(Model model, @RequestParam(value = FieldNames.ID, required=false) Long id) {
		RoleDto roleDto;
		if(id != null){
			roleDto = roleService.getRoleById(id);
		}else {
			roleDto = new RoleDto();
		}
		model.addAttribute("roleDto", roleDto);
		return "addRole";
	}
	
	@PostMapping(value = RequestUrls.DELETE_ROLE)
	public String deleteRoleById(Model model, @PathVariable(FieldNames.ID) Long id) {
		roleService.deleteRoleById(id);
		return "roles";
	}
	
	@ResponseBody
	@PostMapping(value = RequestUrls.DELETE_BULK_ROLES)
	public boolean deleteRoles(@RequestBody IdsDto idsDto) {
		return roleService.deleteRoles(idsDto.getIds());
	}
	
	@ResponseBody
	@PostMapping(value = RequestUrls.DELETE_ALL_ROLES)
	public boolean deleteAllRoles() {
		return roleService.deleteAllRoles();
	}
}
