package com.app.ecom.store.service.impl;

import java.util.HashSet;
import java.util.Set;

import com.app.ecom.store.dto.PrivilegeDto;
import com.app.ecom.store.mapper.PrivilegeMapper;
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
	public Set<PrivilegeDto> getPrivileges() {
		return privilegeMapper.privilegesToPrivilegeDtos(new HashSet<>(privilegeRepository.findAll()));
	}
}