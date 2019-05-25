package com.app.ecom.store.dto;

import java.util.Set;

public class RoleDto {
	private Long id;

	private String name;
	
	private Set<PrivilegeDto> privilegeDtos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<PrivilegeDto> getPrivilegeDtos() {
		return privilegeDtos;
	}

	public void setPrivilegeDtos(Set<PrivilegeDto> privilegeDtos) {
		this.privilegeDtos = privilegeDtos;
	}
}
