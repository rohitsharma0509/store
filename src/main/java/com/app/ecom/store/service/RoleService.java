package com.app.ecom.store.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.ecom.store.model.Role;

public interface RoleService {
	Page<Role> getRoles(Pageable pageable);

	Role addRole(Role role);

	Role getRoleById(Long id);
}
