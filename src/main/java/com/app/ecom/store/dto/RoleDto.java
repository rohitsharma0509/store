package com.app.ecom.store.dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
public class RoleDto {
	
	@JsonProperty("id")
	private Long id;

	@JsonProperty("name")
	private String name;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("userDtos")
	private Set<UserDto> userDtos;
	
	@JsonProperty("privilegeDtos")
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<UserDto> getUserDtos() {
		return userDtos;
	}

	public void setUserDtos(Set<UserDto> userDtos) {
		this.userDtos = userDtos;
	}

	public Set<PrivilegeDto> getPrivilegeDtos() {
		return privilegeDtos;
	}

	public void setPrivilegeDtos(Set<PrivilegeDto> privilegeDtos) {
		this.privilegeDtos = privilegeDtos;
	}
}
