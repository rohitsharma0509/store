package com.app.ecom.store.service;

import java.util.List;
import java.util.Set;

import com.app.ecom.store.dto.RoleDto;
import com.app.ecom.store.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
	Page<Role> getRoles(Pageable pageable);
	
	Set<RoleDto> getRolesByIdIn(List<Long> ids);

	RoleDto addRole(RoleDto roleDto);
	
	RoleDto getRoleByName(String name);

	RoleDto getRoleById(Long id);

	boolean deleteRoleById(Long id);

	boolean deleteRoles(List<Long> ids);
	
	Set<RoleDto> getAllRoles();

	boolean deleteAllRoles();
}
