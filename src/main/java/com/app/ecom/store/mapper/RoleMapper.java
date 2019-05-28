package com.app.ecom.store.mapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.app.ecom.store.dto.RoleDto;
import com.app.ecom.store.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RoleMapper {

	@Autowired
	private PrivilegeMapper privilegeMapper;

	public Set<Role> roleDtosToRoles(Set<RoleDto> roleDtos) {
		if (CollectionUtils.isEmpty(roleDtos)) {
			return Collections.emptySet();
		}

		Set<Role> roles = new HashSet<>();
		roleDtos.stream().filter(Objects::nonNull).forEach(roleDto -> roles.add(roleDtoToRole(roleDto)));
		return roles;
	}

	public Role roleDtoToRole(RoleDto roleDto) {
		Role role = new Role();
		role.setName(roleDto.getName());
		role.setPrivileges(privilegeMapper.privilegeDtosToPrivileges(roleDto.getPrivilegeDtos()));
		return role;
	}
	
	public Set<RoleDto> rolesToRoleDtos(Set<Role> roles) {
		if (CollectionUtils.isEmpty(roles)) {
			return Collections.emptySet();
		}

		Set<RoleDto> roleDtos = new HashSet<>();
		roles.stream().filter(Objects::nonNull).forEach(role -> roleDtos.add(roleToRoleDto(role)));
		return roleDtos;
	}

	public RoleDto roleToRoleDto(Role role) {
		RoleDto roleDto = new RoleDto();
		roleDto.setName(role.getName());
		roleDto.setPrivilegeDtos(privilegeMapper.privilegesToPrivilegeDtos(role.getPrivileges()));
		return roleDto;
	}
}
