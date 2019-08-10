package com.app.ecom.store.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
	public RoleDto getRoleByName(String name) {
		return roleMapper.roleToRoleDto(roleRepository.findByName(name));
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
	public boolean deleteRoleById(Long id) {
		boolean flag = false;
		try {
			roleRepository.deleteById(id);
			flag = true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
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

	@Override
	public Set<RoleDto> getRolesByIdIn(List<Long> ids) {
		return roleMapper.rolesToRoleDtos(roleRepository.findByIdIn(ids));
	}

	@Override
	public Set<RoleDto> getAllRoles() {
		List<Role> roles = roleRepository.findAll();
		return roleMapper.rolesToRoleDtos(new HashSet<>(roles));
	}
}
