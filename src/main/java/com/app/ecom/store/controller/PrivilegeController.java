package com.app.ecom.store.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.app.ecom.store.constants.FieldNames;
import com.app.ecom.store.constants.RequestUrls;
import com.app.ecom.store.dto.CustomPage;
import com.app.ecom.store.dto.IdsDto;
import com.app.ecom.store.dto.PrivilegeDto;
import com.app.ecom.store.dto.Response;
import com.app.ecom.store.service.PrivilegeService;
import com.app.ecom.store.util.CommonUtil;
import com.app.ecom.store.validator.PrivilegeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
public class PrivilegeController {
	
	@Autowired
	private PrivilegeService privilegeService;
	
	@Autowired
	private PrivilegeValidator privilegeValidator;
	
	@Autowired
	private CommonUtil commonUtil;

	
	@GetMapping(value = RequestUrls.PRIVILEGES)
    public String getRoles(Model model, @PageableDefault(page=1, size=10) Pageable pageable, @RequestParam(required=false) String name) {
		Map<String, String> params = new HashMap<>();
		params.put("name", name);
		CustomPage<PrivilegeDto> page = privilegeService.getPrivileges(pageable, params);
		model.addAttribute(FieldNames.PAGGING, commonUtil.getPagging(RequestUrls.PRIVILEGES, page.getPageNumber()+1, page.getTotalPages(), params));
		model.addAttribute(FieldNames.PAGE, page);
        return "privileges";
    }
	
	@GetMapping(value = RequestUrls.ADD_PRIVILEGE)
	public String addPrivilege(Model model, @RequestParam(value = FieldNames.ID, required=false) Long id) {
		PrivilegeDto privilegeDto;
		if(id != null){
			privilegeDto = privilegeService.getPrivilegeById(id);
		}else {
			privilegeDto = new PrivilegeDto();
		}
		model.addAttribute(FieldNames.PRIVILEGE_DTO, privilegeDto);
		List<PrivilegeDto> allPrivileges = privilegeService.getAllPrivileges();
		model.addAttribute("privileges", allPrivileges == null ? Collections.emptyList() : allPrivileges.stream().filter(p-> !p.getId().equals(id)).collect(Collectors.toList()));
		return "addPrivilege";
	}
	
	@PostMapping(value = RequestUrls.PRIVILEGES)
	public String addPrivilege(Model model, @Valid PrivilegeDto privilegeDto, BindingResult bindingResult) {
		privilegeValidator.validate(privilegeDto, bindingResult);
		if (bindingResult.hasErrors()) {
			return "addPrivilege";
		}
		
		privilegeService.addPrivilege(privilegeDto);
		return "redirect:"+RequestUrls.PRIVILEGES;
	}
	
	@ResponseBody
	@PostMapping(value = RequestUrls.DELETE_PRIVILEGE)
	public Response deletePrivilegeById(Model model, @PathVariable(FieldNames.ID) Long id) {
		Response response = privilegeValidator.validatePrivilegeAssociation(Arrays.asList(id));
		if(HttpStatus.OK.value() == response.getCode()) {
			privilegeService.deletePrivilegeById(id);
		}
		return response;
	}
	
	@ResponseBody
	@PostMapping(value = RequestUrls.DELETE_BULK_PRIVILEGES)
	public Response deleteRoles(@RequestBody IdsDto idsDto) {
		Response response = privilegeValidator.validatePrivilegeAssociation(idsDto.getIds());
		if(HttpStatus.OK.value() == response.getCode()) {
			privilegeService.deletePrivileges(idsDto.getIds());
		}
		return response;
	}
	
	@ResponseBody
	@PostMapping(value = RequestUrls.DELETE_ALL_PRIVILEGES)
	public Response deleteAllRoles() {
		Response response = privilegeValidator.validatePrivilegeAssociation(null);
		if(HttpStatus.OK.value() == response.getCode()) {
			privilegeService.deleteAllPrivileges();
		}
		return response;
	}

}
