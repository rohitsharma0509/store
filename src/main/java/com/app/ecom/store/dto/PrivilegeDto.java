package com.app.ecom.store.dto;

import java.util.Set;

public class PrivilegeDto {
	private Long id;
	
	private String name;
	
	private String description;
	
	private Long parentId;
	
	private Boolean isInRole;
	
	private Set<PrivilegeDto> childPrivileges;

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

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Boolean getIsInRole() {
		return isInRole;
	}

	public void setIsInRole(Boolean isInRole) {
		this.isInRole = isInRole;
	}

	public Set<PrivilegeDto> getChildPrivileges() {
		return childPrivileges;
	}

	public void setChildPrivileges(Set<PrivilegeDto> childPrivileges) {
		this.childPrivileges = childPrivileges;
	}

	@Override
	public String toString() {
		return "PrivilegeDto [id=" + id + ", name=" + name + ", description=" + description + ", parentId=" + parentId
				+ ", isInRole=" + isInRole + ", childPrivileges=" + childPrivileges + "]";
	}	
}
