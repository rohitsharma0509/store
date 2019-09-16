package com.app.ecom.store.service;

import java.util.List;

import com.app.ecom.store.dto.PrivilegeDto;

public interface PrivilegeService {

	List<PrivilegeDto> getPrivileges();
	
	PrivilegeDto getPrivilegeById(Long id);

}
