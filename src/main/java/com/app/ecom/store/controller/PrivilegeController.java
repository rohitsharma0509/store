package com.app.ecom.store.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.app.ecom.store.constants.RequestUrls;
import com.app.ecom.store.dto.PrivilegeDto;
import com.app.ecom.store.dto.RoleDto;
import com.app.ecom.store.service.PrivilegeService;
import com.app.ecom.store.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PrivilegeController {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PrivilegeService privilegeService;

	
	@GetMapping(value=RequestUrls.PRIVILEGES)
	public String getPrivileges(Model model, @RequestParam(name="id") Long roleId) {
		RoleDto roleDto = roleService.getRoleById(roleId);
		
		List<Long> userPrivileges = roleDto.getPrivilegeDtos().stream().map(PrivilegeDto::getId).collect(Collectors.toList());
		
		List<PrivilegeDto> allPrivileges = privilegeService.getPrivileges();
		Set<Long> childPrivilegeIds = new HashSet<>();
		allPrivileges.stream().forEach(privilegeDto -> {
			if(userPrivileges.contains(privilegeDto.getId())) {
				privilegeDto.setIsInRole(true);
			} else {
				privilegeDto.setIsInRole(false);
			}
			Set<PrivilegeDto> childPrivileges = allPrivileges.stream().filter(p-> null != p.getParentId() && p.getParentId().equals(privilegeDto.getId())).collect(Collectors.toSet());
			childPrivilegeIds.addAll(childPrivileges.stream().map(PrivilegeDto::getId).collect(Collectors.toSet()));
			privilegeDto.setChildPrivileges(childPrivileges);
		});
		Set<PrivilegeDto> privilegesToDisplay = allPrivileges.stream().filter(p->!childPrivilegeIds.contains(p.getId())).collect(Collectors.toSet());
		model.addAttribute("privilegeDtos", privilegesToDisplay);
		return "privileges";
	}

}
