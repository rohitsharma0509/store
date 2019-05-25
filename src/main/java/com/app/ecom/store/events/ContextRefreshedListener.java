package com.app.ecom.store.events;

import java.util.HashSet;
import java.util.Set;

import com.app.ecom.store.dto.PrivilegeDto;
import com.app.ecom.store.dto.RoleDto;
import com.app.ecom.store.dto.UserDto;
import com.app.ecom.store.model.User;
import com.app.ecom.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
	
	boolean isDone = false;
	
	@Autowired
	private UserService userService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(!isDone) {
			User user = userService.findByUsername("rohits");
			if (null == user) {
				UserDto userDto = new UserDto();
				userDto.setFirstName("Rohit");
				userDto.setLastName("Sharma");
				userDto.setUsername("rohits");
				userDto.setPassword("Admin@123");
				userDto.setIsEnabled(true);
				userDto.setEmail("rohitsharm0509@gmail.com");
				userDto.setLanguage("en");
				userDto.setMobile("8860254047");
				userDto.setRoles(getAdminRoleDto());
				userService.createUser(userDto);
				isDone = true;
			}
		}
	}

	private Set<RoleDto> getAdminRoleDto() {
		Set<RoleDto> roleDtos = new HashSet<>();
		RoleDto roleDto = new RoleDto();
		roleDto.setName("ROLE_ADMIN");
		roleDto.setPrivilegeDtos(getAdminPrivilegeDto());
		roleDtos.add(roleDto);
		return roleDtos;
	}

	private Set<PrivilegeDto> getAdminPrivilegeDto() {
		Set<PrivilegeDto> privilegeDtos = new HashSet<>();
		PrivilegeDto privilegeDto = new PrivilegeDto();
		privilegeDto.setName("ADMIN");
		privilegeDto.setDescription("Is Admin?");
		privilegeDtos.add(privilegeDto);
		return privilegeDtos;
	}
}