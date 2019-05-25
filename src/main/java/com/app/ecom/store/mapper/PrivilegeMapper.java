package com.app.ecom.store.mapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
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
		privilegeDtos.stream().filter(Objects::nonNull)
				.forEach(privilegeDto -> privileges.add(privilegeDtoToprivileges(privilegeDto)));
		return privileges;
	}

	private Privilege privilegeDtoToprivileges(PrivilegeDto privilegeDto) {
		Privilege privilege = new Privilege();
		privilege.setName(privilegeDto.getName());
		privilege.setDescription(privilegeDto.getDescription());
		return privilege;
	}
}
