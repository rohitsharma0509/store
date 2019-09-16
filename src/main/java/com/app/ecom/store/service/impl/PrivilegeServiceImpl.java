package com.app.ecom.store.service.impl;

import java.util.List;
import java.util.Optional;

import com.app.ecom.store.dto.PrivilegeDto;
import com.app.ecom.store.mapper.PrivilegeMapper;
import com.app.ecom.store.model.Privilege;
import com.app.ecom.store.repository.PrivilegeRepository;
import com.app.ecom.store.service.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegeServiceImpl implements PrivilegeService {
	
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired
	private PrivilegeMapper privilegeMapper;

	@Override
	public List<PrivilegeDto> getPrivileges() {
		return privilegeMapper.privilegesToPrivilegeDtos(privilegeRepository.findAll());
	}

	@Override
	public PrivilegeDto getPrivilegeById(Long id) {
		Optional<Privilege> optionalPrivilege = privilegeRepository.findById(id);
		return optionalPrivilege.isPresent() ? privilegeMapper.privilegeToPrivilegeDto(optionalPrivilege.get()) : null;
	}
}