package com.app.ecom.store.mapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.app.ecom.store.dto.PrivilegeDto;
import com.app.ecom.store.model.Privilege;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class PrivilegeMapper {
	public Set<Privilege> privilegeDtosToPrivileges(Set<PrivilegeDto> privilegeDtos) {
		if (CollectionUtils.isEmpty(privilegeDtos)) {
			return Collections.emptySet();
		}

		Set<Privilege> privileges = new HashSet<>();
		privilegeDtos.stream().forEach(privilegeDto -> privileges.add(privilegeDtoToPrivilege(privilegeDto)));
		return privileges;
	}

	private Privilege privilegeDtoToPrivilege(PrivilegeDto privilegeDto) {
		if(null == privilegeDto) {
			return null;
		}
		
		Privilege privilege = new Privilege();
		privilege.setId(privilegeDto.getId());
		privilege.setName(privilegeDto.getName());
		privilege.setDescription(privilegeDto.getDescription());
		privilege.setParentId(privilegeDto.getParentId());
		return privilege;
	}

	public Set<PrivilegeDto> privilegesToPrivilegeDtos(Set<Privilege> privileges) {
		if (CollectionUtils.isEmpty(privileges)) {
			return Collections.emptySet();
		}
		
		Set<PrivilegeDto> privilegeDtos = new HashSet<>();
		privileges.stream().forEach(privilege -> privilegeDtos.add(privilegeToPrivilegeDto(privilege)));
		return privilegeDtos;
	}

	private PrivilegeDto privilegeToPrivilegeDto(Privilege privilege) {
		if(null == privilege) {
			return null;
		}
		
		PrivilegeDto privilegeDto = new PrivilegeDto();
		privilegeDto.setId(privilege.getId());
		privilegeDto.setName(privilege.getName());
		privilegeDto.setDescription(privilege.getDescription());
		privilegeDto.setParentId(privilege.getParentId());
		return privilegeDto;
	}
}
