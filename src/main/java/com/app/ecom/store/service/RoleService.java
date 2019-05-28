package com.app.ecom.store.service;

import com.app.ecom.store.dto.RoleDto;
import com.app.ecom.store.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
	Page<Role> getRoles(Pageable pageable);

	RoleDto addRole(RoleDto roleDto);

	RoleDto getRoleById(Long id);
}
