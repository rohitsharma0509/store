package com.app.ecom.store.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.app.ecom.store.dto.RoleDto;
import com.app.ecom.store.mapper.RoleMapper;
import com.app.ecom.store.model.Role;
import com.app.ecom.store.repository.RoleRepository;
import com.app.ecom.store.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Page<Role> getRoles(Pageable pageable) {
		PageRequest request = PageRequest.of(pageable.getPageNumber() - 1, pageable.getPageSize(), pageable.getSort());
		return roleRepository.findAll(request);
	}

	public RoleDto addRole(RoleDto roleDto) {
		return roleMapper.roleToRoleDto(roleRepository.save(roleMapper.roleDtoToRole(roleDto)));
	}

	@Override
	public RoleDto getRoleById(Long id) {
		Optional<Role> optionalRole = roleRepository.findById(id);
		if(optionalRole.isPresent()) {
			return roleMapper.roleToRoleDto(optionalRole.get());
		} else {
			return null;
		}
	}
	
	@Override
	@Transactional
	public void deleteRoleById(Long id) {
		roleRepository.deleteById(id);
	}
	
	@Override
	@Transactional
	public boolean deleteAllRoles() {
		boolean isDeleted = false;
		try {
			roleRepository.deleteAll();
			isDeleted = true;
		} catch(Exception e) {
			
		}
		return isDeleted;
	}

	@Override
	@Transactional
	public boolean deleteRoles(List<Long> ids) {
		boolean isDeleted = false;
		try {
			roleRepository.deleteByIdIn(ids);
			isDeleted = true;
		} catch(Exception e) {
			
		}
		return isDeleted;
	}
}
